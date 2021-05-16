//
// Created by loki on 2021/1/19.
//

#ifndef SKIPLIST_H_
#define SKIPLIST_H_


#include <atomic>
#include <random>
#include <cstdlib>
#include "backend/learning/leveldb/arena.h"


class Arena;


template<typename Key, class Comparator>
class SkipList {
public:
    struct Node;

public:
    explicit SkipList(Comparator cmp, Arena *arena);

    SkipList(const SkipList &) = delete;

    SkipList &operator=(const SkipList &) = delete;

    void Insert(const Key &key);

    bool Contains(const Key &key) const;

private:
    enum {
        kMaxHeight = 12
    };

    inline int GetMaxHeight() const {
        return max_height_.load(std::memory_order_relaxed);
    }

    Node *NewNode(const Key &key, int height);

    int RandomHeight();

    bool Equal(const Key &a, const Key &b) const {
        return compare_(a, b) == 0;
    }

    bool KeyIsAfterNode(const Key &key, Node *n) const;

    Node *FindGreaterOrEqual(const Key &key, Node **prev) const;

    Node *FindLessThan(const Key &key) const;

    Node *FindLast() const;

    Comparator const compare_;
    Arena *const arena_;

    Node *const head_;

    std::atomic<int> max_height_;

    std::default_random_engine defEngine;
    std::uniform_int_distribution<int> intDistro;
};

template<typename Key, class Comparator>
struct SkipList<Key, Comparator>::Node {
    explicit Node(const Key &k) : key(k) {}

    Key const key;

    Node *Next(int n) {
        assert(n >= 0);
        return next_[n].load(std::memory_order_acquire);
    }

    void SetNext(int n, Node *x) {
        assert(n >= 0);
        next_[n].store(x, std::memory_order_release);
    }

    Node *NoBarrier_Next(int n) {
        assert(n >= 0);
        return next_[n].load(std::memory_order_relaxed);
    }

    void NoBarrier_SetNext(int n, Node *x) {
        assert(n >= 0);
        next_[n].store(x, std::memory_order_relaxed);
    }

private:
    std::atomic<Node *> next_[1];
};

template<typename Key, class Comparator>
typename SkipList<Key, Comparator>::Node *SkipList<Key, Comparator>::NewNode(const Key &key, int height) {
    char *const node_memory = arena_->AllocateAligned(sizeof(Node) + sizeof(std::atomic<Node *>) * (height - 1));
    return new(node_memory)Node(key);
}

template<typename Key, class Comparator>
SkipList<Key, Comparator>::SkipList(Comparator cmp, Arena *arena)
        :compare_(cmp),
         arena_(arena),
         head_(NewNode(0, kMaxHeight)),
         max_height_(1) {
    for (int i = 0; i < kMaxHeight; ++i) {
        head_->SetNext(i, nullptr);
    }
}

template<typename Key, class Comparator>
void SkipList<Key, Comparator>::Insert(const Key &key) {
    Node *prev[kMaxHeight];
    Node *x = FindGreaterOrEqual(key, prev);
    assert(x == nullptr || !Equal(key, x->key));
    int height = RandomHeight();
    if (height > GetMaxHeight()) {
        for (int i = GetMaxHeight(); i < height; ++i) {
            prev[i] = head_;
        }

        max_height_.store(height, std::memory_order_relaxed);
    }

    x = NewNode(key, height);
    for (int i = 0; i < height; ++i) {
        x->NoBarrier_SetNext(i, prev[i]->NoBarrier_Next(i));
        prev[i]->SetNext(i, x);
    }


}


template<typename Key, class Comparator>
int SkipList<Key, Comparator>::RandomHeight() {
    static const unsigned int kBranching = 4;
    int height = 1;
    while (height < kMaxHeight && ((intDistro(defEngine) % kBranching) == 0)) {
        height++;
    }
    assert(height > 0);
    assert(height <= kMaxHeight);
    return height;
};

template<typename Key, class Comparator>
bool SkipList<Key, Comparator>::KeyIsAfterNode(const Key &key, Node *n) const {
    return (n != nullptr) && (compare_(n->key, key) < 0);
}


template<typename Key, class Comparator>
typename SkipList<Key, Comparator>::Node *
SkipList<Key, Comparator>::FindGreaterOrEqual(const Key &key, Node **prev) const {
    Node *x = head_;
    int level = GetMaxHeight() - 1;
    while (true) {
        Node *next = x->Next(level);
        if (KeyIsAfterNode(key, next)) {
            x = next;
        } else {
            if (prev != nullptr) {
                prev[level] = x;
            }
            if (level == 0) {
                return next;
            } else {
                level--;
            }
        }
    }

}

template<typename Key, class Comparator>
typename SkipList<Key, Comparator>::Node *SkipList<Key, Comparator>::FindLast() const {
    Node *x = head_;
    int level = GetMaxHeight() - 1;
    while (true) {
        Node *next = x->Next(level);
        if (next == nullptr) {
            if (level == 0) {
                return x;
            } else {
                level--;
            }
        } else {
            x = head_;
        }
    }
}

template<typename Key, class Comparator>
bool SkipList<Key, Comparator>::Contains(const Key &key) const {
    Node *x = FindGreaterOrEqual(key, nullptr);
    if (x != nullptr && Equal(key, x->key)) {
        return true;
    } else {
        return false;
    }
}


#endif //SKIPLIST_H_

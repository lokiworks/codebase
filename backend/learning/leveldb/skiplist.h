//
// Created by loki on 2021/1/19.
//

#ifndef SKIPLIST_H_
#define SKIPLIST_H_


#include <atomic>
#include <random>

class Arena;


template<typename Key, class Comparator>
class SkipList {
public:
    struct Node;

public:
    explicit SkipList(Comparator cmp, Arena* arena);

    SkipList(const SkipList&) = delete ;

    SkipList& operator=(const  SkipList&) = delete ;

    void Insert(const Key& key);

    bool Contains(const Key& key) const ;

private:
    enum {kMaxHeight = 12};

    inline int GetMaxHeight() const {
        return 0;
    }

    Node* NewNode(const Key& key, int height);
    int RandomHeight();
    bool Equal(const Key& a, const Key& b) const {
        return true;
    }

    bool KeyIsAfterNode(const Key& key, Node* n) const ;

    Node* FindGreaterOrEqual(const Key & key, Node** prev) const ;

    Node* FindLessThan(const Key& key) const ;

    Node* FindLast() const ;

    Comparator const compare_;
    Arena* const arena_;

    Node* const head_;

    std::atomic<int> max_height_;

    std::default_random_engine defEngine;
    std::uniform_int_distribution<int> intDistro;
};

template<typename Key, class Comparator>
struct SkipList<Key, Comparator>::Node{
    explicit Node(const Key&k):key(k){}

    Key const key ;

    Node * Next(int n){
        assert(n>=0);
        return next_[n].load(std::memory_order_acquire);
    }
    void SetNext(int n, Node*x){
        assert(n>=0);
        next_[n].store(x, std::memory_order_release);
    }

    Node* NoBarrier_Next(int n){
        assert(n >=0);
        return next_[n].load(std::memory_order_relaxed);
    }

    void NoBarrier_SetNext(int n, Node*x){
        assert(n>=0);
        next_[n].store(x, std::memory_order_relaxed);
    }

private:
    std::atomic<Node*> next_[1];
};

template<typename Key, class Comparator>
typename SkipList<Key, Comparator>::Node *SkipList<Key, Comparator>::NewNode(const Key &key, int height) {
    return nullptr;
}


#endif //SKIPLIST_H_

//
// Created by loki on 2021/1/24.
//

#ifndef COMPARATOR_H_
#define COMPARATOR_H_


class Slice;

class Comparator {
public:
    virtual ~Comparator();

    virtual int Compare(const Slice &a, const Slice &b) const = 0;
};

#endif //COMPARATOR_H_

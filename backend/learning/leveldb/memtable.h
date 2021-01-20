//
// Created by loki on 2021/1/19.
//

#ifndef MEMTABLE_H_
#define MEMTABLE_H_

class InternalKeyComparator;


class MemTable {
public:
    explicit MemTable(const InternalKeyComparator& comparator);

    MemTable(const MemTable&) = delete;

    MemTable& operator=(const MemTable&) = delete ;

    void Ref(){

    }

private:
    ~MemTable();

};


#endif //MEMTABLE_H_

package com.valentine.lockdemo;

static final class Node {
    // 共享
    static final Node SHARED = new Node();
    // 独占
    static final Node EXCLUSIVE = null;
    // 取消
    static final int CANCELLED = 1;
    // 信号
    static final int SIGNAL = -1;
    // 状态
    static final int CONDITION = -2;
    // 传播
    static final int PROPAGATE = -3;

    //表示节点的状态，包含cancelled（取消）；condition 表示节点在等待condition也就是在condition队列中
    volatile int waitStatus;
    //前继节点
    volatile Node prev;
    //前继节点
    volatile Node next;
    //当前线程
    volatile Thread thread;
    //存储在condition队列中的后继节点
    Node nextWaiter;

    final boolean isShared() {
        return nextWaiter == SHARED;
    }

    final Node predecessor() throws NullPointerException {
        Node p = prev;
        if (p == null)
            throw new NullPointerException();
        else
            return p;
    }

    Node() {    // Used to establish initial head or SHARED marker
    }

    Node(Thread thread, Node mode) {     // Used by addWaiter
        this.nextWaiter = mode;
        this.thread = thread;
    }

    Node(Thread thread, int waitStatus) { // Used by Condition
        this.waitStatus = waitStatus;
        this.thread = thread;
    }
}

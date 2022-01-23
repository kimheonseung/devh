package com.devh.common.datastructure;

import java.io.Serializable;

/*
 * <pre>
 * Description :
 *     자료구조 - 원형 큐
 *     꽉 찬 경우 덮어쓰지 않고 대기하는 큐
 * ===============================
 * Memberfields :
 *
 * ===============================
 *
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 8.
 * </pre>
 */
public class CircularQueue implements Serializable {
    private static final long serialVersionUID = -4217658401154211406L;

    private int front;
    private int rear;
    private int maxSize;
    private Object[] queueArray;

    public CircularQueue(int maxSize) {
        this.front = 0;
        this.rear  = 0;
        /* Size 1인 경우 full, empty 판단을 위해 2로 설정 */
        this.maxSize = maxSize == 1? 2 : maxSize;
        this.queueArray = new Object[this.maxSize];
    }


    /*
     * <pre>
     * Description :
     *     Full 체크
     *     가장 뒤의 포인터와 가장 앞의 포인터가 1 차이 날 때
     * ===============================
     * Parameters :
     *
     * Returns :
     *     boolean
     * Throws :
     *
     * ===============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 11. 8.
     * </pre>
     */
    public boolean isFull() {
        return (rear + 1) % (maxSize) == front;
    }

    /*
     * <pre>
     * Description :
     *     Empty 체크
     *     가장 뒤의 포인터와 가장 앞의 포인터가 같을 때
     * ===============================
     * Parameters :
     *
     * Returns :
     *
     * Throws :
     *
     * ===============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 11. 8.
     * </pre>
     */
    public boolean isEmpty() {
        return rear == front;
    }

    /*
     * <pre>
     * Description :
     *     큐에 데이터 넣기
     *     큐가 꽉 찬 경우 0.1초 대기
     *     rear 포인터를 1 증가시킨 뒤 해당 포인터에 데이터 삽입
     * ===============================
     * Parameters :
     *     Object item
     * Returns :
     *
     * Throws :
     *
     * ===============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 11. 8.
     * </pre>
     */
    public synchronized void enqueue(Object item) {
        while(isFull()) {
            try {
                Thread.sleep(100L);
            } catch(InterruptedException ignored) {}
        }
        rear = (rear + 1) % maxSize;
        queueArray[rear] = item;
    }

    /*
     * <pre>
     * Description :
     *     큐의 데이터 꺼내기
     *     큐가 비어있는 경우 null 반환
     *     그렇지 않은 경우 front 포인터를 1 증가시킨 뒤 해당 포인터의 데이터 꺼내기
     * ===============================
     * Parameters :
     *
     * Returns :
     *     Object
     * Throws :
     *
     * ===============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 11. 8.
     * </pre>
     */
    public synchronized Object dequeue() {
        if(isEmpty())
            return null;
        else {
            front = (front + 1) % maxSize;
            return queueArray[front];
        }
    }
}

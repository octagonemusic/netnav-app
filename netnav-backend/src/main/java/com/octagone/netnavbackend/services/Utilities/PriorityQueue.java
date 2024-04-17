package com.octagone.netnavbackend.services.Utilities;

import java.util.ArrayList;

public class PriorityQueue<T extends Comparable<T>> {
    // implement PQ using min-heap and array list
    private ArrayList<T> heap;

    public PriorityQueue() {
        heap = new ArrayList<T>();
    }

    public void insert(T element) {
        heap.add(element);
        int index = heap.size() - 1;
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).compareTo(heap.get(parentIndex)) < 0) {
                T temp = heap.get(index);
                heap.set(index, heap.get(parentIndex));
                heap.set(parentIndex, temp);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    public T extractMin() {
        if (heap.size() == 0) {
            return null;
        }
        T min = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        int index = 0;
        while (true) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = 2 * index + 2;
            if (leftChildIndex >= heap.size()) {
                break;
            }
            int minIndex = index;
            if (heap.get(leftChildIndex).compareTo(heap.get(minIndex)) < 0) {
                minIndex = leftChildIndex;
            }
            if (rightChildIndex < heap.size() && heap.get(rightChildIndex).compareTo(heap.get(minIndex)) < 0) {
                minIndex = rightChildIndex;
            }
            if (minIndex == index) {
                break;
            } else {
                T temp = heap.get(index);
                heap.set(index, heap.get(minIndex));
                heap.set(minIndex, temp);
                index = minIndex;
            }
        }
        return min;
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void display() {
        for (T element : heap) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
}
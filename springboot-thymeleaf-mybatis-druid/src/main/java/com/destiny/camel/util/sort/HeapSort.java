package com.destiny.camel.util.sort;

public class HeapSort {
	
	public static int[] heapSort(int[] arr) {
		
		if (arr == null && arr.length == 0) {
			throw new RuntimeException("数组不合法");
		}
		
		// 构建堆（从最下面叶子结点层的上一层，即倒数第二层的第一个开始进行构建调整）
		for (int i = arr.length / 2 - 1; i >= 0; i--) {
			adjustDown(arr, i, arr.length);
		}
		
		// 循环调整下沉
		for (int i = arr.length - 1; i >= 0; i--) {
			swap(arr, 0, i);
			adjustDown(arr, 0, i);
		}
		
		return arr;
	}
	
	/**
	 * 调整
	 *
	 * @param arr
	 * @param parent
	 * @param length
	 */
	public static void adjustDown(int[] arr, int parent, int length) {
		// 临时元素保存要下沉的元素
		int temp = arr[parent];
		// 左节点的位置
		int leftChild = 2 * parent + 1;
		// 开始往下调整
		while (leftChild < length) {
			// 如果右孩子节点比左孩子大，则定位到右孩子 (父节点只是比左右孩子都大，左右孩子大小不确定)
			if (leftChild + 1 < length && arr[leftChild] < arr[leftChild + 1]) {
				// 此时leftChild实际上是右孩子，但始终是左右里面最大的
				leftChild++;
			}
			//  大顶堆条件被破坏了
			if (arr[leftChild] <= temp) {
				break;
			}
			// 把子节点中大的值赋值给父节点
			arr[parent] = arr[leftChild];
			// 大的子节点为父节点，调整它的左右子节点
			parent = leftChild;
			leftChild = 2 * parent + 1;
		}
		arr[parent] = temp;
	}
	
	/**
	 * 交换数组中两个元素
	 *
	 * @param arr   数组
	 * @param i     父元素
	 * @param index 元素2
	 */
	public static void swap(int[] arr, int i, int index) {
		int temp = arr[i];
		arr[i] = arr[index];
		arr[index] = temp;
	}
	
	public static void main(String[] args) {
		//int[] arr = {5, 7, 8, 3, 1, 2, 4, 6, 8};
		int[] arr = {3, 1, 2, 4};
		//int[] arr = {1, 2, 3};
		arr = heapSort(arr);
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}
	
	
}

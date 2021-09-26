package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, category, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� �߰�]\n"
				+ "ī�װ� > ");
		category = sc.next();
		
		sc.nextLine();
		System.out.print("���� > ");
		title = sc.nextLine().trim();
		
		if (list.isDuplicate(title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
			return;
		}
		
//		sc.nextLine();
//		System.out.print("ī�װ� > ");
//		category = sc.nextLine();
		
//		sc.nextLine();
		System.out.print("���� > ");
		desc = sc.nextLine().trim();
		
		System.out.print("��������(yyyy/mm/dd) > ");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t);
		System.out.println("�߰��Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� ����]\n"
				+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
//		String title = sc.next();
		int i = sc.nextInt();
		System.out.println((i) + ". " + l.getItem(i-1));
		System.out.print("\n�� �׸��� �����Ͻðڽ��ϱ�? (y/n) > ");
		String answer = sc.next();
		if(answer.equals("y")) {
			l.deleteItem(l.getItem(i-1));
			System.out.println("�����Ǿ����ϴ�.");
		}
		
//		for (TodoItem item : l.getList()) {
//			if (title.equals(item.getTitle())) {
//				l.deleteItem(item);
//				System.out.println("�����Ǿ����ϴ�.");
//				break;
//			}
//		}
	}


	public static void updateItem(TodoList l) {
		
		String new_category, new_title, new_description, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� ����]\n"
				+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		int i = sc.nextInt();
		if(i > l.getSize()) {
			System.out.println("�������� �ʴ� ��ȣ�Դϴ�!");
			return;
		}
		
		System.out.println((i) + ". " + l.getItem(i-1));
		
		System.out.print("\n�� �׸��� �����Ͻðڽ��ϱ�? (y/n) > ");
		String answer = sc.next();
		if(answer.equals("y")) 
			l.deleteItem(l.getItem(i-1));
		//
		System.out.print("�� ī�װ� > ");
		new_category = sc.next();
		
		sc.nextLine();
		System.out.print("�� ���� > ");
		new_title = sc.nextLine().trim();
		
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
			return;
		}
		//
//		System.out.print("�� ���� > ");
//		 new_title = sc.next().trim();
//		if (l.isDuplicate(new_title)) {
//			System.out.println("������ �ߺ��˴ϴ�!");
//			return;
//		}
//		
//		sc.nextLine();
//		System.out.print("�� ī�װ� > ");
//		 new_category = sc.nextLine();
		
//		sc.nextLine();
		System.out.print("�� ���� > ");
		 new_description = sc.nextLine().trim();
		
		System.out.print("�� ��������(yyyy/mm/dd) > ");
		 new_due_date = sc.nextLine().trim();
		
//		l.deleteItem(l.getItem(i-1));
		TodoItem t = new TodoItem(new_category, new_title, new_description, new_due_date);
		l.addItem(t);
		System.out.println("�����Ǿ����ϴ�.");
		
//		for (TodoItem item : l.getList()) {
//			if (item.getTitle().equals(title)) {
//				l.deleteItem(item);
//				TodoItem t = new TodoItem(new_title, new_category, new_description, new_due_date);
//				l.addItem(t);
//				System.out.println("�����Ǿ����ϴ�.");
//			}
//		}
	}

	public static void listAll(TodoList l) {
//		int j = 1;
		System.out.println("[��ü ���, �� " + l.getSize() + "��]");
//		for (TodoItem item : l.getList()) {
//			System.out.println(j + ". " + item.toString());
//			j++;
//		}
		for(int i=0; i<l.getSize(); i++)
			System.out.println((i+1) + ". " + l.getItem(i).toString());
	}
	
	public static void ls_cate(TodoList l) {
		
		HashSet<String> hashSet = new HashSet<>();
		ArrayList<String> arrs = new ArrayList();
		
		for (TodoItem item : l.getList()) {
			hashSet.add(item.getCategory());
		}
		for (Object o : hashSet) {
			arrs.add(o.toString());
        }
		String str = "";
		for(String arr : arrs) {
			str += arr + " / ";
		}
		
		System.out.println(str.substring(0, str.length()-3));
		System.out.println("�� " + hashSet.size() + "���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.");
	}
	
	public static void find(TodoList l, String find) {
		
		int i = 0, count = 0;
		
		for (TodoItem item : l.getList()) {
			if (item.getTitle().contains(find)) {
				System.out.println((i+1) + ". " + l.getItem(i).toString());
				count++;
			}
			else if(item.getDesc().contains(find)) {
				System.out.println((i+1) + ". " + l.getItem(i).toString());
				count++;
			}
			i++;
		}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void find_cate(TodoList l, String find) {
		
		int i = 0, count = 0;
		
		for (TodoItem item : l.getList()) {
			if (item.getCategory().contains(find)) {
				System.out.println((i+1) + ". " + l.getItem(i).toString());
				count++;
			}
			i++;
		}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}

	public static void loadList(TodoList l, String string) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(string));
			String oneline;
			while((oneline = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline, "##");
				String title = st.nextToken();
				String category = st.nextToken();
			    String desc = st.nextToken();
			    String due_date = st.nextToken();
			    String current_date = st.nextToken();
			    
			    TodoItem t = new TodoItem(title, category, desc, due_date, current_date);
			    l.addItem(t);
			}
			br.close();
			System.out.println(l.getSize() + "���� �׸��� �о����ϴ�.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveList(TodoList l, String string) {
		try {
			Writer w = new FileWriter(string);
			for(TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

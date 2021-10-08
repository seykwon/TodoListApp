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
		
		System.out.print("[항목 추가]\n"
				+ "카테고리 > ");
		category = sc.next();
		
		sc.nextLine();
		System.out.print("제목 > ");
		title = sc.nextLine().trim();
		
		if (list.isDuplicate(title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		
		System.out.print("마감일자(yyyy/mm/dd) > ");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t);
		System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n"
				+ "삭제할 항목의 번호를 입력하시오 > ");
		int i = sc.nextInt();
		if(i > l.getSize()) {
			System.out.println("없는 번호입니다!");
			return;
		}
		System.out.println((i) + ". " + l.getItem(i-1).toString());
		
		System.out.print("\n위 항목을 삭제하시겠습니까? (y/n) > ");
		String answer = sc.next();
		if(answer.equals("y")) {
			l.deleteItem(l.getItem(i-1));
			System.out.println("삭제되었습니다.");
		}
	}


	public static void updateItem(TodoList l) {
		
		String new_category, new_title, new_description, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]\n"
				+ "수정할 항목의 번호를 입력하시오 > ");
		int i = sc.nextInt();
		if(i > l.getSize()) {
			System.out.println("존재하지 않는 번호입니다!");
			return;
		}
		
		System.out.println((i) + ". " + l.getItem(i-1));
		
		System.out.print("\n위 항목을 수정하시겠습니까? (y/n) > ");
		String answer = sc.next();
		if(answer.equals("y")) 
			l.deleteItem(l.getItem(i-1));
		//
		System.out.print("새 카테고리 > ");
		new_category = sc.next();
		
		sc.nextLine();
		System.out.print("새 제목 > ");
		new_title = sc.nextLine().trim();
		
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		System.out.print("새 내용 > ");
		 new_description = sc.nextLine().trim();
		
		System.out.print("새 마감일자(yyyy/mm/dd) > ");
		 new_due_date = sc.nextLine().trim();
		

		TodoItem t = new TodoItem(new_category, new_title, new_description, new_due_date);
		l.addItem(t);
		System.out.println("수정되었습니다.");
	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록, 총 " + l.getSize() + "개]");

		for(int i=0; i<l.getSize(); i++)
			System.out.println((i+1) + ". " + l.getItem(i).toString());
	}
	
	public static void ls_cate(TodoList l) {
		
		Set<String> clist = new HashSet<>();
		ArrayList<String> arrs = new ArrayList();
		
		for(TodoItem item : l.getList()) {
			clist.add(item.getCategory());
		}
		
		Iterator it = clist.iterator();
		while(it.hasNext()) {
			String s = (String)it.next();
			System.out.print(s);
			if(it.hasNext())
				System.out.print(" / ");
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", clist.size());
	}
	
	public static void find(TodoList l, String find) {
		
		int count = 0;
		for(int i=0; i<l.getSize(); i++) {
			if(l.getItem(i).getTitle().contains(find)||l.getItem(i).getDesc().contains(find)) {
				System.out.println((i+1) + ". " + l.getItem(i).toString());
				count++;
			}
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void find_cate(TodoList l, String find) {
		
		int count = 0;
		
		for(int i=0; i<l.getSize(); i++) {
			if(l.getItem(i).getCategory().contains(find)) {
				System.out.println((i+1) + ". " + l.getItem(i).toString());
				count++;
			}
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
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
			System.out.println(l.getSize() + "개의 항목을 읽었습니다.");
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

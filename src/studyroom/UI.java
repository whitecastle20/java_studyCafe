package studyroom;
import java.util.Scanner;
import java.io.*;

public class UI {
	public static void main(String args[]) {
		int userAnswer;        // ����� �޴� ��ȣ �Է�
		String roomName;    // ���͵�� �̸�
		int pricePerHour;    //���͵�� 1�ð� �� ����
		int capacity;    // ���͵�� �ִ� �ο� ��
		int wantPeopleCount;    // ���ϴ� ������� �ο�
		int month; //��
		int date; //��¥
		int hour; //�ð�
		String phoneNumber; //��ȭ��ȣ


		Management manager = new Management();
		Scanner scan = new Scanner(System.in);

		final String saveFileName = "studyroom.save";
		DataOutputStream out = null;
		DataOutputStream in = null;


		while (true) {
			System.out.println("����ð� : " + manager.getCurrentDateTimeString());
			System.out.print("��ȣ �Է�(1.�� ���� 2.�� ���� 3.n�ο� ��� ã�� 4.���� 5.�� �Խ� 6.�� ��� 7.Ư������ ���⺸�� 8.n�ð��ڷ� 9:���� ���� 10.���� �б�) : ");
			userAnswer = scan.nextInt();        // ��ȣ(��) �Է¹ޱ�
			scan.nextLine();            // ���๮�� ����
			if (userAnswer == 1) {    // 1���� �Է����� ��� (�� ����)
				System.out.print("���͵�� �̸�: ");
				roomName = scan.nextLine();        // ���͵�� �̸� �Է¹ޱ�

				System.out.print("�ð� �� ����: ");
				pricePerHour = scan.nextInt();  //�ð� �� ���� �Է¹ޱ�
				scan.nextLine();                    // ���๮�� ����

				System.out.print("�ִ� �ο�: ");
				capacity = scan.nextInt();        // �ִ� �ο� �Է¹ޱ�
				scan.nextLine();                        // ���๮�� ����
				manager.addRoom(capacity, pricePerHour, roomName); // Management Ŭ������ addRoom()�޼ҵ� ȣ���ؼ� �Է¹��� �� �Ű������� �������ֱ�


			} else if (userAnswer == 2) {  // 2���� �Է����� ��� (�� ����)
				System.out.print("������ ���͵�� �̸��� �Է��ϼ���: ");
				roomName = scan.nextLine();    //������ ���͵�� �̸� �Է¹ޱ�
				manager.removeRoom(roomName);        // Management Ŭ������ removeRoom()�޼ҵ� ȣ���ؼ� �Է¹��� �� �Ű������� �������ֱ�

			} else if (userAnswer == 3) {        // 3���� �Է����� ��� (n�ο� ��� ã��)
				System.out.print("�� �ο� ���͵���� ���Ͻʴϱ�?");
				wantPeopleCount = scan.nextInt();        // ���ϴ� �ο� �� �Է¹ޱ�
				scan.nextLine();
				try {
					Room[] usableRooms = manager.searchUsableRooms(wantPeopleCount);    //Management Ŭ������ searchUsableRooms() �޼ҵ�� �迭�� ��ȯ�޾Ƽ� usuableRooms�� ����
					for (Room usableRoom : usableRooms) { //�迭 �ݺ���
						System.out.println("---��� ������ ���͵�� ����---");
						System.out.println("���͵�� �̸�" + usableRoom.getRoomName());    // ��밡���� ���͵���� �̸� ���
						System.out.println("��� ������ �ִ� �ο� �� : " + usableRoom.getCapacity());    // ��밡���� ���͵���� ������ �ִ� �ο� �� ���
						System.out.println("-------------------------");


					}
				} catch (Exception e) {        // ��밡���� ���� �������� ���� ���
					scan = new Scanner(System.in);    //��ĳ���� ���׸� �ذ��ϱ� ���� �ڵ�
					System.out.println("�˼��մϴ�. ��� ������ ���� �����ϴ�.");
				}
			} else if (userAnswer == 4) {    // 4���� �Է����� ��� (����)
				System.out.println("����");
				break;    // ���ѷ��� Ż��
			} else if (userAnswer == 5) {// 5���� �Է����� ��� (�� �Խ�)
				try {
					System.out.print("�Խ��Ͻ� ���͵�� �̸��� �Է����ּ���: ");
					roomName = scan.nextLine();
					User user = new User();
					System.out.print("ȸ������(��ȭ��ȣ)�� �Է����ּ���: ");
					phoneNumber = scan.nextLine();
					user.setPhoneNumber(phoneNumber);
					manager.enterRoom(roomName, user);    //���͵��, ��ȭ��ȣ �޾Ƽ� enterRoom()�޼ҵ� ���ڷ� �Ѱ��ֱ�
					System.out.println(manager.getCurrentDateTimeString() + " �Խ� �Ϸ�Ǿ����ϴ�.");
				} catch (Exception e) { //�ͼ��� �߻�
					scan = new Scanner(System.in);
					System.out.println("�Խ� ����");
				}


			} else if (userAnswer == 6) {// 6���� �Է����� ��� (�� ���)
				try {
					System.out.print("����Ϸ��� ������� ��ȭ��ȣ�� �Է����ּ���: ");
					phoneNumber = scan.nextLine();
					User user = manager.outRoom(phoneNumber);// ��ȭ��ȣ�� outRoom()�޼ҵ� ���ڿ� �־��ְ� ��ȯ�� ����� ��ü�� user�� �ֱ�
					System.out.println("�Խǽð� : " + manager.getDateTimeString(user.getEnterDateTime()));    // ManagementŬ������ getDateTimeString()�޼����� ���ڿ� ����� ���� �ð� �־ ���ڿ��� ���
					System.out.println("��ǽð� : " + manager.getCurrentDateTimeString());
					System.out.println("��� �Ϸ�Ǿ����ϴ�. (�̿�ݾ� : " + user.getPay() + "��)"); //��� ������ �Բ� �̿�ݾ� ����ϱ�
				} catch (Exception e) {    //�ͼ��� �߻�
					scan = new Scanner(System.in);
					System.out.println("��� �����ϼ̽��ϴ�.");
				}
			} else if (userAnswer == 7) { // 7���� �Է����� ���(Ư�� ��¥ ���⺸��)
				System.out.print("������ ���� ���ϴ� ���� �Է����ּ���.(1��-12��) : ");
				month = scan.nextInt();
				scan.nextLine();
				System.out.print("������ ���� ���ϴ� ��¥�� �Է����ּ���.(1��-31��) : ");
				date = scan.nextInt();
				scan.nextLine();
				System.out.println(month + " �� " + date + " ���� ������ " + manager.totalIncome(month, date) + "  �� �Դϴ�."); //date�� �Է¹޾� totalIncome()�޼ҵ��� ���ڷ� �Ѱ��־� ��ȯ���� �� ���
			} else if (userAnswer == 8) {// 8���� �Է����� ���(���� ��¥�� �Ѿ��, ����������)
				System.out.println("����ð� : " + manager.getCurrentDateTimeString());    // ManagementŬ������ getCurrentDateTimeString() �޼ҵ带 �̿��� ���� �ð� ���
				System.out.print("������ �ð��� �Է����ּ���. : ");
				hour = scan.nextInt();
				scan.nextLine();
				manager.nextHour(hour);    //nextHour() �޼ҵ� ȣ��
			} else if (userAnswer == 9) {// 9���� �Է����� ���(���� ����)
				try (	//OutputStream ��ü ���� �� �Ҵ�
						FileOutputStream fos = new FileOutputStream(saveFileName);
						DataOutputStream dos = new DataOutputStream(fos);
				) {
					dos.writeLong(manager.getCurrentDateTime().getTimeInMillis());	
					dos.writeInt(manager.roomCount);		
					for (int i = 0; i < manager.roomCount; i++) {	//�� ���� ����
						dos.writeInt(manager.room[i].capacity);	
						dos.writeInt(manager.room[i].pricePerHour);
						dos.writeUTF(manager.room[i].roomName);
						dos.writeInt(manager.room[i].nowUsingCount);
						for (User user : manager.room[i].users) {	//����� ���� ����
							dos.writeUTF(user.getPhoneNumber());
							dos.writeLong(user.getEnterDateTime().getTimeInMillis());
						}
					}
					for (int i = 1; i <= 12; i++) {		//���� ����
						for (int j = 1; j <= 31; j++) {
							if (manager.totalIncome[i][j] > 0) {
								dos.writeInt(i);	
								dos.writeInt(j);
								dos.writeInt(manager.totalIncome[i][j]);
							}
						}
					}

					System.out.println("���忡 �����Ͽ����ϴ�.");
				} catch (Exception e) {
					System.out.println("���忡 �����Ͽ����ϴ�.");
				}
			} else if (userAnswer == 10) {// 10���� �Է����� ���(���� �б�)
				try (	//InputStream ��ü ���� �� �Ҵ�
						FileInputStream fis = new FileInputStream(saveFileName);
						DataInputStream dis = new DataInputStream(fis);
				) {
					manager = new Management(dis.readLong());	
					int roomCount = dis.readInt();		
					for (int i = 0; i < roomCount; i++) {	//�� ���� �б�
						capacity = dis.readInt();
						pricePerHour = dis.readInt();
						roomName = dis.readUTF();
						manager.addRoom(capacity, pricePerHour, roomName);
						int nowUsingCount = dis.readInt();
						for (int j = 0; j < nowUsingCount; j++) {	//��������� �б�
							User user = new User();
							user.setPhoneNumber(dis.readUTF());
							manager.enterRoom(roomName, user, dis.readLong());
						}
					}
					while (true) { // ���� ������ �̵��ϸ� EOFException �߻��ϸ鼭 ����
						int i = dis.readInt();
						int j = dis.readInt();
						manager.totalIncome[i][j] = dis.readInt();	//���� �б�
					}
					
				} catch (EOFException ignored) {
					System.out.println("�ҷ����� �����Ͽ����ϴ�.");
				} catch (Exception e) {
					System.out.println("�ҷ����µ� �����Ͽ����ϴ�.");
				}
			}
		}
		scan.close();            //scan �ݱ�
	}
}




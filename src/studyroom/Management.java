package studyroom;

import java.util.GregorianCalendar;
import java.io.*;
import java.util.Calendar;

public class Management {
	GregorianCalendar calendar = new GregorianCalendar();
	Room[] room = null; // �迭 ���͵��
	protected int roomCount = 0;  //���͵���� ����
	protected int[][] totalIncome = new int[13][32]; // �� ��¥������ ����

	public Management() {//�� ������ �Լ�
	}

	public Management(long currentTimeInMillis) {	//���� �ð��� ���ڷ� ���� ������ �Լ�
		calendar.setTimeInMillis(currentTimeInMillis);
	}

	void addRoom(int maxCount, int price, String name) {  // ���͵�� �߰� �Լ�
		Room[] temp = room;	// ���Ƿ� ���� temp �迭�� room �迭 ����
		room = new Room[roomCount+1];// ���͵�� ���� ������Ű�� ���͵�� ũ�� ����
		for(int i=0; i< roomCount; i++)	// temp�� ���͵�� ũ�� ������ room�� �־��ֱ�
			room[i] = temp[i];

		room[roomCount] = new Room();		// roomCount����ŭ ���͵�� ��ü �����
		room[roomCount].setCapacity(maxCount); // setCapacity()�޼ҵ带 �̿��� ���� �߰��� ���͵�� �ִ� ũ�� ����
		room[roomCount].setPricePerHour(price);// setPricePerHour()�޼ҵ带 �̿��� ���� �߰��� ���͵�� �ð��� ���� ����
		room[roomCount].setRoomName(name);//  setRoomName()�޼ҵ带 �̿��� ���� �߰��� ���͵�� �̸� ����

		roomCount += 1;	// ���͵�� ���� �ϳ� ����
	}

	void removeRoom(String roomName) {
		int index = -1;// ������ �ε��� �����ϱ� ���� ����
		for(int i =0; i<roomCount; i++) {  //�Լ��� �Ű������� ���� �̸��� ������ �� ã�� ���� �ݺ���
			if(roomName.equals(room[i].getRoomName())) {
				index = i;// ������ ���� �ε����� ����
			}
		}
		if (index == -1) {// �Ű������� ������ �̸��� ���� ���
			System.out.println("������ �̸��� ���� ���� ������ �Ұ����մϴ�.");
			return;
		}
		Room temp2[] = room; // ���Ƿ� ���� temp2 �迭�� room �迭 ����
		room = new Room[roomCount-1];	// ���͵���� ũ�⸦ 1�� ���� ������ŭ�� ���� room �迭
		for(int i=0; i<index; i++) {//�ݺ����� �̿��� �ε��� �������� temp2�迭�� �ִ� ������ ũ�⸦ ���� room�迭�� �ֱ�
			room[i] = temp2[i];
		}
		for(int i=index; i<roomCount-1; i++) {  // �ݺ����� �̿��� �ε��� ���� ������ room�迭�� �־��ִµ�, temp2�� ���� ���̱� ������ temp2�� i+1��°�� room�� i��°�� �ֱ�
			room[i] = temp2[i+1];
		}
		roomCount -= 1;	// ���͵�� ���� �ϳ� ���̱�
	}


	Room[] searchUsableRooms(int wantPeopleCount)throws Exception {		//��밡���� �� �迭 ��ȯ �Լ�
		Room[] usableRooms = new Room[0]; 	//��ȯ�ϱ� ���� ��밡���� ���� ���ϴ� usuableRooms �迭 ũ�⸦ 0���� �����ؼ�--
		Room[] temp3;	// ���� �迭�� temp3

		for(int i=0;i<roomCount;i++) {	// �� �����Ǿ��ִ� ����� ������ŭ �ݺ����� �̿���
			if(room[i].getCapacity() == wantPeopleCount) {	//����ڰ� ���ϴ� �ο����� ���� �ִ� �뷮�� ���� ���̶��
				temp3 = usableRooms;		// ������ �迭 temp3�� usableRooms �����س���
				usableRooms = new Room[temp3.length+1];// --ã�� ������ usableRooms �迭�� ũ�⸦ 1���� �ø���.
				for(int j=0;j<temp3.length;j++) {		// �ݺ����� �̿��� ������ �迭 temp3 ������ usuableRooms �迭�� ����
					usableRooms[j] = temp3[j];
				}
				usableRooms[usableRooms.length-1] = room[i];// -1�� �Ἥ ���� �þ ������ ĭ�� ã�� room[i]�� ����
			}
		}
		if(usableRooms.length == 0) {	// ��밡���� ���� ���� ��� �ͼ��� �߻�
			throw new Exception("��� ������ ���� �����ϴ�.");
		}

		return usableRooms;	//usableRooms �迭 ��ȯ
	}

	void enterRoom(String roomName,User user, Long timeMillis) throws Exception {	// �� �Խ� �Լ�
		user.setEnterDateTime(timeMillis); //UserŬ������ setEnterDateTime()ȣ���ؼ� ����ð� ����

		boolean possible = false;
		for(int i=0; i<roomCount; i++) {
			if(room[i].getRoomName().equals(roomName) && room[i].getNowUsingCount() < room[i].getCapacity()) { // �� �̸��� �ְ� �ִ��ο����� ���� ������� �ο��� ���� ��
				possible = true;
				room[i].addUser(user);	//RoomŬ������ addUser()�޼ҵ� ȣ��
			}
		}
		if(!possible) {	// ���ǹ� ���� ���ҽ�
			throw new Exception("�Խ� �Ұ��մϴ�.");
		}
	}

	void enterRoom(String roomName,User user) throws Exception {	// �� �Խ� �Լ�
		enterRoom(roomName, user, getCurrentDateTime().getTimeInMillis());
	}

	User outRoom(String phoneNumber) throws Exception {	// �� ��� �Լ�
		Room room = null;
		for(int i=0; i<roomCount; i++) {
			for(int j=0;j<this.room[i].nowUsingCount;j++) { //���� ������� �ο���ŭ�� �ݺ��� ����
				if(this.room[i].users[j].getPhoneNumber().equals(phoneNumber)) { //������� ��ȭ��ȣ�� ��ġ�� ��
					room = this.room[i];
					break;
				}
			}
		}

		if(room == null) {	//ã�� ������ ���
			throw new Exception("����");	//�ͼ��� �߻�
		}

		User user = room.removeUser(phoneNumber);	//RoomŬ������ removeUser() �޼ҵ� ȣ���ؼ� ���� ��ü ����
		//�� ��¥���� ���̸� ��� ���� getTimeInMiillis() �̿�
		long enterDateTimeMillis = user.getEnterDateTime().getTimeInMillis();
		long outDateTimeMillis = getCurrentDateTime().getTimeInMillis();

		// �̿�ݾ� ��ȯ���ֱ� ���� pay ������ ����
		int durationHours = (int)(outDateTimeMillis - enterDateTimeMillis) / 60 / 60 / 1000; //���� ��ȯ
		int pay = durationHours * room.getPricePerHour();	//�̿�ݾ� = �̿�ð� X 1�ð��� ����
		user.setPay(pay);

		int outMonth = getCurrentDateTime().get(Calendar.MONTH) + 1; //���� 0-11���� ���ϵǴ� +1
		int outDate = getCurrentDateTime().get(Calendar.DATE);	// �� ���
		totalIncome[outMonth][outDate] += pay;	//totalIncome �迭�� �����ؼ� ���� ���ϱ�

		return user;
	}

	int totalIncome(int month, int date) { //Ư�� ��¥ ���� ��ȯ
		return totalIncome[month][date];
	}

	GregorianCalendar getCurrentDateTime() {	//GregorianCalendarŬ������ calendar��ü ��ȯ
		return calendar;
	}

	String getCurrentDateTimeString() {	//calendar ��ü�� getDateTimeString()�޼ҵ带 �̿��� string Ÿ������ ��ȯ(UI���� ����ð� ����ϱ� ���� ���� �Լ�)
		return getDateTimeString(calendar);
	}

	String getDateTimeString(Calendar calendar) {// ��,��,��,����/����,�ø� format ���ڿ��� �����ؼ� ����
		String format = calendar.get(Calendar.YEAR) + "�� "
				+ (calendar.get(Calendar.MONTH) + 1) + "�� "
				+ calendar.get(Calendar.DATE) + "�� ";
		if(calendar.get(Calendar.AM_PM) == 0) {	//0�̸� (����/���� �����ϱ� ���� ���ǹ�)
			format += "���� ";
		}
		else {	//1�̸�
			format += "���� ";
		}
		format += calendar.get(Calendar.HOUR) + "��";
		return format;
	}

	void nextHour(int hour) {	// �ð��� ���������� ���������ִ� �Լ�
		calendar.add(Calendar.HOUR, hour);
		for(int i=0;i<roomCount;i++) {
			room[i].initTotalIncome();
		}
	}

}
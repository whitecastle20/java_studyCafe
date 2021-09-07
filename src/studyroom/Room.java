package studyroom;

import java.io.*;

public class Room {
	protected int capacity; // �ش� ���͵���� �ִ� �ο� ��
	protected String roomName; // �ش� ���͵���� �̸�
	protected int pricePerHour; // �ش� ���͵���� �ð� �� ����
	private int totalIncome; // �� ������ �׳��� ������ ����
	private int enterTime; // ���� �ð�
	private int outTime; // ��� �ð�

	protected int nowUsingCount; //���� ������� �ο���
	protected User[] users = new User[0]; //����� ��ü

	Room(int capacity,int pricePerHour, String roomName){  // ���͵�� ������
		this.capacity = capacity;
		this.pricePerHour = pricePerHour;
		this.roomName = roomName;
	}
	public Room() { }	//�� ������
	void setRoomName(String roomName){	// ���͵�� �̸� ����
		this.roomName = roomName;
	}
	String getRoomName(){	// ���͵�� �̸� ��ȯ
		return roomName;
	}
	void setPricePerHour(int pricePerHour){ //���͵�� �ð� �� ���� ����
		this.pricePerHour = pricePerHour;
	}
	int getPricePerHour() { 	//1�ð� �� ���� ��ȯ
		return pricePerHour;
	}

	void setCapacity(int capacity){//���͵�� �ִ� �ο� ����
		this.capacity = capacity;
	}
	int getCapacity(){	// ���͵�� �ִ� �ο� ��ȯ
		return capacity;
	}

	int getEnterTime() {	// ���� �ð� ��ȯ
		return enterTime;
	}
	int getOutTime() {	// ���� �ð� ��ȯ
		return outTime;
	}
	void setEnterTime(int enterTime) { 	//���� �ð� ����
		this.enterTime = enterTime;
	}
	void setOutTime(int outTime) {		//���� �ð� ����
		this.outTime = outTime;
	}

	int getNowUsingCount() //���� ������� �ο��� ��ȯ
	{
		return nowUsingCount;
	}

	void addUser(User user) {	// ����� �߰��Լ�
		User[] tmp = users;
		users = new User[nowUsingCount+1];	// ���͵���� ũ�⸦ 1�� �ø� ������ŭ�� ���� room �迭
		for(int i=0; i<nowUsingCount; i++) {//�ݺ����� �̿��� �ε��� �������� tmp�迭�� �ִ� ������ ũ�⸦ �ø� room�迭�� �ֱ�
			users[i] = tmp[i];
		}
		users[nowUsingCount] = user;
		nowUsingCount++;	// ���� ���� ������� �ο��� ����
	}

	User removeUser(String phoneNumber) throws Exception {	//��ȭ��ȣ�� �̿��� ����� ���� �Լ�
		int userIndex = -1;
		for(int i=0;i<users.length;i++) {
			if(users[i].getPhoneNumber().equals(phoneNumber)) {
				userIndex = i;
				break;
			}
		}
		if (userIndex == -1) {
			throw new Exception("����");
		}

		User user = users[userIndex];
		User[] tmp = users;
		users = new User[nowUsingCount-1];	// ���͵���� ũ�⸦ 1�� ���� ������ŭ�� ���� room �迭
		for(int i=0; i<userIndex; i++) {//�ݺ����� �̿��� �ε��� �������� tmp�迭�� �ִ� ������ ũ�⸦ ���� room�迭�� �ֱ�
			users[i] = tmp[i];
		}
		for(int i=userIndex; i<users.length; i++) {  // �ݺ����� �̿��� �ε��� ���� ������ room�迭�� �־��ִµ�, tmp�� ���� ���̱� ������ tmp�� i+1��°�� room�� i��°�� �ֱ�
			users[i] = tmp[i+1];
		}
		nowUsingCount--;	// ���� ���� ������� �ο��� ����

		return user;
	}

	void initTotalIncome() {	//���� �ٲ� �� totalIncome�� 0���� �ʱ�ȭ�ϱ� ���� ���� �Լ�
		totalIncome = 0;
	}

}

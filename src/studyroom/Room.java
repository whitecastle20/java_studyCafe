package studyroom;

import java.io.*;

public class Room {
	protected int capacity; // 해당 스터디룸의 최대 인원 수
	protected String roomName; // 해당 스터디룸의 이름
	protected int pricePerHour; // 해당 스터디룸의 시간 당 가격
	private int totalIncome; // 이 변수에 그날의 매출을 저장
	private int enterTime; // 입장 시간
	private int outTime; // 퇴실 시간

	protected int nowUsingCount; //현재 사용중인 인원수
	protected User[] users = new User[0]; //사용자 객체

	Room(int capacity,int pricePerHour, String roomName){  // 스터디룸 생성자
		this.capacity = capacity;
		this.pricePerHour = pricePerHour;
		this.roomName = roomName;
	}
	public Room() { }	//빈 생성자
	void setRoomName(String roomName){	// 스터디룸 이름 설정
		this.roomName = roomName;
	}
	String getRoomName(){	// 스터디룸 이름 반환
		return roomName;
	}
	void setPricePerHour(int pricePerHour){ //스터디룸 시간 당 가격 설정
		this.pricePerHour = pricePerHour;
	}
	int getPricePerHour() { 	//1시간 당 가격 반환
		return pricePerHour;
	}

	void setCapacity(int capacity){//스터디룸 최대 인원 설정
		this.capacity = capacity;
	}
	int getCapacity(){	// 스터디룸 최대 인원 반환
		return capacity;
	}

	int getEnterTime() {	// 입장 시간 반환
		return enterTime;
	}
	int getOutTime() {	// 퇴장 시간 반환
		return outTime;
	}
	void setEnterTime(int enterTime) { 	//입장 시간 설정
		this.enterTime = enterTime;
	}
	void setOutTime(int outTime) {		//퇴장 시간 설정
		this.outTime = outTime;
	}

	int getNowUsingCount() //현재 사용중인 인원수 반환
	{
		return nowUsingCount;
	}

	void addUser(User user) {	// 사용자 추가함수
		User[] tmp = users;
		users = new User[nowUsingCount+1];	// 스터디룸의 크기를 1개 늘린 개수만큼을 가진 room 배열
		for(int i=0; i<nowUsingCount; i++) {//반복문을 이용해 인덱스 전까지는 tmp배열에 있는 내용을 크기를 늘린 room배열에 넣기
			users[i] = tmp[i];
		}
		users[nowUsingCount] = user;
		nowUsingCount++;	// 방의 현재 사용중인 인원수 증가
	}

	User removeUser(String phoneNumber) throws Exception {	//전화번호를 이용한 사용자 삭제 함수
		int userIndex = -1;
		for(int i=0;i<users.length;i++) {
			if(users[i].getPhoneNumber().equals(phoneNumber)) {
				userIndex = i;
				break;
			}
		}
		if (userIndex == -1) {
			throw new Exception("실패");
		}

		User user = users[userIndex];
		User[] tmp = users;
		users = new User[nowUsingCount-1];	// 스터디룸의 크기를 1개 줄인 개수만큼을 가진 room 배열
		for(int i=0; i<userIndex; i++) {//반복문을 이용해 인덱스 전까지는 tmp배열에 있는 내용을 크기를 줄인 room배열에 넣기
			users[i] = tmp[i];
		}
		for(int i=userIndex; i<users.length; i++) {  // 반복문을 이용해 인덱스 후의 내용을 room배열에 넣어주는데, tmp는 삭제 전이기 때문에 tmp의 i+1번째를 room의 i번째에 넣기
			users[i] = tmp[i+1];
		}
		nowUsingCount--;	// 방의 현재 사용중인 인원수 감소

		return user;
	}

	void initTotalIncome() {	//날이 바뀔 때 totalIncome를 0으로 초기화하기 위해 만든 함수
		totalIncome = 0;
	}

}

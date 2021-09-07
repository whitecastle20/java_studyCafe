package studyroom;

import java.util.GregorianCalendar;
import java.io.*;
import java.util.Calendar;

public class Management {
	GregorianCalendar calendar = new GregorianCalendar();
	Room[] room = null; // 배열 스터디룸
	protected int roomCount = 0;  //스터디룸의 개수
	protected int[][] totalIncome = new int[13][32]; // 각 날짜마다의 매출

	public Management() {//빈 생성자 함수
	}

	public Management(long currentTimeInMillis) {	//현재 시간을 인자로 갖는 생성자 함수
		calendar.setTimeInMillis(currentTimeInMillis);
	}

	void addRoom(int maxCount, int price, String name) {  // 스터디룸 추가 함수
		Room[] temp = room;	// 임의로 만든 temp 배열에 room 배열 대입
		room = new Room[roomCount+1];// 스터디룸 개수 증가시키고 스터디룸 크기 수정
		for(int i=0; i< roomCount; i++)	// temp를 스터디룸 크기 수정한 room에 넣어주기
			room[i] = temp[i];

		room[roomCount] = new Room();		// roomCount개만큼 스터디룸 객체 만들기
		room[roomCount].setCapacity(maxCount); // setCapacity()메소드를 이용해 새로 추가한 스터디룸 최대 크기 설정
		room[roomCount].setPricePerHour(price);// setPricePerHour()메소드를 이용해 새로 추가한 스터디룸 시간당 가격 설정
		room[roomCount].setRoomName(name);//  setRoomName()메소드를 이용해 새로 추가한 스터디룸 이름 설정

		roomCount += 1;	// 스터디룸 개수 하나 증가
	}

	void removeRoom(String roomName) {
		int index = -1;// 삭제할 인덱스 저장하기 위한 변수
		for(int i =0; i<roomCount; i++) {  //함수의 매개변수에 들어온 이름과 동일한 룸 찾기 위한 반복문
			if(roomName.equals(room[i].getRoomName())) {
				index = i;// 동일한 룸의 인덱스를 저장
			}
		}
		if (index == -1) {// 매개변수와 동일한 이름이 없을 경우
			System.out.println("동일한 이름의 방이 없어 삭제가 불가능합니다.");
			return;
		}
		Room temp2[] = room; // 임의로 만든 temp2 배열에 room 배열 대입
		room = new Room[roomCount-1];	// 스터디룸의 크기를 1개 줄인 개수만큼을 가진 room 배열
		for(int i=0; i<index; i++) {//반복문을 이용해 인덱스 전까지는 temp2배열에 있는 내용을 크기를 줄인 room배열에 넣기
			room[i] = temp2[i];
		}
		for(int i=index; i<roomCount-1; i++) {  // 반복문을 이용해 인덱스 후의 내용을 room배열에 넣어주는데, temp2는 삭제 전이기 때문에 temp2의 i+1번째를 room의 i번째에 넣기
			room[i] = temp2[i+1];
		}
		roomCount -= 1;	// 스터디룸 개수 하나 줄이기
	}


	Room[] searchUsableRooms(int wantPeopleCount)throws Exception {		//사용가능한 방 배열 반환 함수
		Room[] usableRooms = new Room[0]; 	//반환하기 위한 사용가능한 방을 뜻하는 usuableRooms 배열 크기를 0부터 시작해서--
		Room[] temp3;	// 임의 배열인 temp3

		for(int i=0;i<roomCount;i++) {	// 총 생성되어있는 방들의 개수만큼 반복문을 이용해
			if(room[i].getCapacity() == wantPeopleCount) {	//사용자가 원하는 인원수와 같은 최대 용량을 가진 방이라면
				temp3 = usableRooms;		// 임의의 배열 temp3에 usableRooms 대입해놓기
				usableRooms = new Room[temp3.length+1];// --찾을 때마다 usableRooms 배열의 크기를 1개씩 늘린다.
				for(int j=0;j<temp3.length;j++) {		// 반복문을 이용해 임의의 배열 temp3 내용을 usuableRooms 배열에 대입
					usableRooms[j] = temp3[j];
				}
				usableRooms[usableRooms.length-1] = room[i];// -1을 써서 새로 늘어난 마지막 칸에 찾은 room[i]를 대입
			}
		}
		if(usableRooms.length == 0) {	// 사용가능한 방이 없을 경우 익셉션 발생
			throw new Exception("사용 가능한 방이 없습니다.");
		}

		return usableRooms;	//usableRooms 배열 반환
	}

	void enterRoom(String roomName,User user, Long timeMillis) throws Exception {	// 방 입실 함수
		user.setEnterDateTime(timeMillis); //User클래스의 setEnterDateTime()호출해서 입장시간 설정

		boolean possible = false;
		for(int i=0; i<roomCount; i++) {
			if(room[i].getRoomName().equals(roomName) && room[i].getNowUsingCount() < room[i].getCapacity()) { // 방 이름이 있고 최대인원보다 현재 사용중인 인원이 적을 때
				possible = true;
				room[i].addUser(user);	//Room클래스의 addUser()메소드 호출
			}
		}
		if(!possible) {	// 조건문 만족 안할시
			throw new Exception("입실 불가합니다.");
		}
	}

	void enterRoom(String roomName,User user) throws Exception {	// 방 입실 함수
		enterRoom(roomName, user, getCurrentDateTime().getTimeInMillis());
	}

	User outRoom(String phoneNumber) throws Exception {	// 방 퇴실 함수
		Room room = null;
		for(int i=0; i<roomCount; i++) {
			for(int j=0;j<this.room[i].nowUsingCount;j++) { //현재 사용중인 인원만큼만 반복문 돌기
				if(this.room[i].users[j].getPhoneNumber().equals(phoneNumber)) { //사용자의 전화번호와 일치할 때
					room = this.room[i];
					break;
				}
			}
		}

		if(room == null) {	//찾지 못했을 경우
			throw new Exception("실패");	//익셉션 발생
		}

		User user = room.removeUser(phoneNumber);	//Room클래스의 removeUser() 메소드 호출해서 얻은 객체 대입
		//두 날짜간의 차이를 얻기 위해 getTimeInMiillis() 이용
		long enterDateTimeMillis = user.getEnterDateTime().getTimeInMillis();
		long outDateTimeMillis = getCurrentDateTime().getTimeInMillis();

		// 이용금액 반환해주기 위해 pay 변수에 저장
		int durationHours = (int)(outDateTimeMillis - enterDateTimeMillis) / 60 / 60 / 1000; //단위 변환
		int pay = durationHours * room.getPricePerHour();	//이용금액 = 이용시간 X 1시간당 가격
		user.setPay(pay);

		int outMonth = getCurrentDateTime().get(Calendar.MONTH) + 1; //월은 0-11까지 리턴되니 +1
		int outDate = getCurrentDateTime().get(Calendar.DATE);	// 일 얻기
		totalIncome[outMonth][outDate] += pay;	//totalIncome 배열에 누적해서 페이 더하기

		return user;
	}

	int totalIncome(int month, int date) { //특정 날짜 매출 반환
		return totalIncome[month][date];
	}

	GregorianCalendar getCurrentDateTime() {	//GregorianCalendar클래스의 calendar객체 반환
		return calendar;
	}

	String getCurrentDateTimeString() {	//calendar 객체를 getDateTimeString()메소드를 이용해 string 타입으로 반환(UI에서 현재시간 출력하기 위해 만든 함수)
		return getDateTimeString(calendar);
	}

	String getDateTimeString(Calendar calendar) {// 년,월,일,오전/오후,시를 format 문자열에 대입해서 리턴
		String format = calendar.get(Calendar.YEAR) + "년 "
				+ (calendar.get(Calendar.MONTH) + 1) + "월 "
				+ calendar.get(Calendar.DATE) + "일 ";
		if(calendar.get(Calendar.AM_PM) == 0) {	//0이면 (오전/오후 연결하기 위한 조건문)
			format += "오전 ";
		}
		else {	//1이면
			format += "오후 ";
		}
		format += calendar.get(Calendar.HOUR) + "시";
		return format;
	}

	void nextHour(int hour) {	// 시간를 인위적으로 증가시켜주는 함수
		calendar.add(Calendar.HOUR, hour);
		for(int i=0;i<roomCount;i++) {
			room[i].initTotalIncome();
		}
	}

}
package studyroom;
import java.util.Scanner;
import java.io.*;

public class UI {
	public static void main(String args[]) {
		int userAnswer;        // 사용자 메뉴 번호 입력
		String roomName;    // 스터디룸 이름
		int pricePerHour;    //스터디룸 1시간 당 가격
		int capacity;    // 스터디룸 최대 인원 수
		int wantPeopleCount;    // 원하는 사용자의 인원
		int month; //월
		int date; //날짜
		int hour; //시간
		String phoneNumber; //전화번호


		Management manager = new Management();
		Scanner scan = new Scanner(System.in);

		final String saveFileName = "studyroom.save";
		DataOutputStream out = null;
		DataOutputStream in = null;


		while (true) {
			System.out.println("현재시간 : " + manager.getCurrentDateTimeString());
			System.out.print("번호 입력(1.룸 생성 2.룸 삭제 3.n인용 빈방 찾기 4.종료 5.룸 입실 6.룸 퇴실 7.특정일자 매출보기 8.n시간뒤로 9:파일 저장 10.파일 읽기) : ");
			userAnswer = scan.nextInt();        // 번호(값) 입력받기
			scan.nextLine();            // 개행문자 제거
			if (userAnswer == 1) {    // 1번을 입력했을 경우 (룸 생성)
				System.out.print("스터디룸 이름: ");
				roomName = scan.nextLine();        // 스터디룸 이름 입력받기

				System.out.print("시간 당 가격: ");
				pricePerHour = scan.nextInt();  //시간 당 가격 입력받기
				scan.nextLine();                    // 개행문자 제거

				System.out.print("최대 인원: ");
				capacity = scan.nextInt();        // 최대 인원 입력받기
				scan.nextLine();                        // 개행문자 제거
				manager.addRoom(capacity, pricePerHour, roomName); // Management 클래스의 addRoom()메소드 호출해서 입력받은 값 매개변수로 전달해주기


			} else if (userAnswer == 2) {  // 2번을 입력했을 경우 (룸 삭제)
				System.out.print("삭제할 스터디룸 이름을 입력하세요: ");
				roomName = scan.nextLine();    //삭제할 스터디룸 이름 입력받기
				manager.removeRoom(roomName);        // Management 클래스의 removeRoom()메소드 호출해서 입력받은 값 매개변수로 전달해주기

			} else if (userAnswer == 3) {        // 3번을 입력했을 경우 (n인용 빈방 찾기)
				System.out.print("몇 인용 스터디룸을 원하십니까?");
				wantPeopleCount = scan.nextInt();        // 원하는 인원 수 입력받기
				scan.nextLine();
				try {
					Room[] usableRooms = manager.searchUsableRooms(wantPeopleCount);    //Management 클래스의 searchUsableRooms() 메소드로 배열을 반환받아서 usuableRooms에 대입
					for (Room usableRoom : usableRooms) { //배열 반복문
						System.out.println("---사용 가능한 스터디룸 정보---");
						System.out.println("스터디룸 이름" + usableRoom.getRoomName());    // 사용가능한 스터디룸의 이름 출력
						System.out.println("사용 가능한 최대 인원 수 : " + usableRoom.getCapacity());    // 사용가능한 스터디룸의 가능한 최대 인원 수 출력
						System.out.println("-------------------------");


					}
				} catch (Exception e) {        // 사용가능한 방이 존재하지 않을 경우
					scan = new Scanner(System.in);    //스캐너의 버그를 해결하기 위한 코드
					System.out.println("죄송합니다. 사용 가능한 방이 없습니다.");
				}
			} else if (userAnswer == 4) {    // 4번을 입력했을 경우 (종료)
				System.out.println("종료");
				break;    // 무한루프 탈출
			} else if (userAnswer == 5) {// 5번을 입력했을 경우 (룸 입실)
				try {
					System.out.print("입실하실 스터디룸 이름을 입력해주세요: ");
					roomName = scan.nextLine();
					User user = new User();
					System.out.print("회원정보(전화번호)를 입력해주세요: ");
					phoneNumber = scan.nextLine();
					user.setPhoneNumber(phoneNumber);
					manager.enterRoom(roomName, user);    //스터디룸, 전화번호 받아서 enterRoom()메소드 인자로 넘겨주기
					System.out.println(manager.getCurrentDateTimeString() + " 입실 완료되었습니다.");
				} catch (Exception e) { //익셉션 발생
					scan = new Scanner(System.in);
					System.out.println("입실 실패");
				}


			} else if (userAnswer == 6) {// 6번을 입력했을 경우 (룸 퇴실)
				try {
					System.out.print("퇴실하려는 사용자의 전화번호를 입력해주세요: ");
					phoneNumber = scan.nextLine();
					User user = manager.outRoom(phoneNumber);// 전화번호를 outRoom()메소드 인자에 넣어주고 반환된 사용자 객체를 user에 넣기
					System.out.println("입실시간 : " + manager.getDateTimeString(user.getEnterDateTime()));    // Management클래스의 getDateTimeString()메서드의 인자에 사용자 입장 시간 넣어서 문자열로 출력
					System.out.println("퇴실시간 : " + manager.getCurrentDateTimeString());
					System.out.println("퇴실 완료되었습니다. (이용금액 : " + user.getPay() + "원)"); //퇴실 성공과 함께 이용금액 출력하기
				} catch (Exception e) {    //익셉션 발생
					scan = new Scanner(System.in);
					System.out.println("퇴실 실패하셨습니다.");
				}
			} else if (userAnswer == 7) { // 7번을 입력했을 경우(특정 날짜 매출보기)
				System.out.print("매출을 보기 원하는 월을 입력해주세요.(1월-12월) : ");
				month = scan.nextInt();
				scan.nextLine();
				System.out.print("매출을 보기 원하는 날짜를 입력해주세요.(1일-31일) : ");
				date = scan.nextInt();
				scan.nextLine();
				System.out.println(month + " 월 " + date + " 일의 매출은 " + manager.totalIncome(month, date) + "  원 입니다."); //date를 입력받아 totalIncome()메소드의 인자로 넘겨주어 반환받은 값 출력
			} else if (userAnswer == 8) {// 8번을 입력했을 경우(다음 날짜로 넘어가기, 인위적으로)
				System.out.println("현재시간 : " + manager.getCurrentDateTimeString());    // Management클래스의 getCurrentDateTimeString() 메소드를 이용해 현재 시간 출력
				System.out.print("지나갈 시간을 입력해주세요. : ");
				hour = scan.nextInt();
				scan.nextLine();
				manager.nextHour(hour);    //nextHour() 메소드 호출
			} else if (userAnswer == 9) {// 9번을 입력했을 경우(파일 저장)
				try (	//OutputStream 객체 선언 및 할당
						FileOutputStream fos = new FileOutputStream(saveFileName);
						DataOutputStream dos = new DataOutputStream(fos);
				) {
					dos.writeLong(manager.getCurrentDateTime().getTimeInMillis());	
					dos.writeInt(manager.roomCount);		
					for (int i = 0; i < manager.roomCount; i++) {	//방 정보 쓰기
						dos.writeInt(manager.room[i].capacity);	
						dos.writeInt(manager.room[i].pricePerHour);
						dos.writeUTF(manager.room[i].roomName);
						dos.writeInt(manager.room[i].nowUsingCount);
						for (User user : manager.room[i].users) {	//사용자 정보 쓰기
							dos.writeUTF(user.getPhoneNumber());
							dos.writeLong(user.getEnterDateTime().getTimeInMillis());
						}
					}
					for (int i = 1; i <= 12; i++) {		//매출 쓰기
						for (int j = 1; j <= 31; j++) {
							if (manager.totalIncome[i][j] > 0) {
								dos.writeInt(i);	
								dos.writeInt(j);
								dos.writeInt(manager.totalIncome[i][j]);
							}
						}
					}

					System.out.println("저장에 성공하였습니다.");
				} catch (Exception e) {
					System.out.println("저장에 실패하였습니다.");
				}
			} else if (userAnswer == 10) {// 10번을 입력했을 경우(파일 읽기)
				try (	//InputStream 객체 선언 및 할당
						FileInputStream fis = new FileInputStream(saveFileName);
						DataInputStream dis = new DataInputStream(fis);
				) {
					manager = new Management(dis.readLong());	
					int roomCount = dis.readInt();		
					for (int i = 0; i < roomCount; i++) {	//방 정보 읽기
						capacity = dis.readInt();
						pricePerHour = dis.readInt();
						roomName = dis.readUTF();
						manager.addRoom(capacity, pricePerHour, roomName);
						int nowUsingCount = dis.readInt();
						for (int j = 0; j < nowUsingCount; j++) {	//사용자정보 읽기
							User user = new User();
							user.setPhoneNumber(dis.readUTF());
							manager.enterRoom(roomName, user, dis.readLong());
						}
					}
					while (true) { // 파일 끝까지 이동하면 EOFException 발생하면서 종료
						int i = dis.readInt();
						int j = dis.readInt();
						manager.totalIncome[i][j] = dis.readInt();	//매출 읽기
					}
					
				} catch (EOFException ignored) {
					System.out.println("불러오기 성공하였습니다.");
				} catch (Exception e) {
					System.out.println("불러오는데 실패하였습니다.");
				}
			}
		}
		scan.close();            //scan 닫기
	}
}




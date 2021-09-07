package studyroom;

import java.io.*;
import java.util.GregorianCalendar;

public class User {
	private String phoneNumber;	// 전화번호
	private GregorianCalendar enterDateTime;	//입장시간
	private int pay;	//이용금액

	User(){	//빈 생성자

	}

	String getPhoneNumber() {	// 전화번호 얻기
		return phoneNumber;
	}

	void setPhoneNumber(String phoneNumber) {	// 전화번호 설정
		this.phoneNumber = phoneNumber;
	}

	GregorianCalendar getEnterDateTime(){	//enterDateTime 반환
		return enterDateTime;
	}

	void setEnterDateTime(long timeMillis) {	//사용자 입장 시간 설정하기
		enterDateTime = new GregorianCalendar();
		enterDateTime.setTimeInMillis(timeMillis);
	}

	int getPay() {	//이용금액 반환
		return pay;
	}

	void setPay(int pay) {	// 인자값으로 받은 pay를 사용자 이용금액으로 설정
		this.pay = pay;
	}
	
	
}

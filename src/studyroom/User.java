package studyroom;

import java.io.*;
import java.util.GregorianCalendar;

public class User {
	private String phoneNumber;	// ��ȭ��ȣ
	private GregorianCalendar enterDateTime;	//����ð�
	private int pay;	//�̿�ݾ�

	User(){	//�� ������

	}

	String getPhoneNumber() {	// ��ȭ��ȣ ���
		return phoneNumber;
	}

	void setPhoneNumber(String phoneNumber) {	// ��ȭ��ȣ ����
		this.phoneNumber = phoneNumber;
	}

	GregorianCalendar getEnterDateTime(){	//enterDateTime ��ȯ
		return enterDateTime;
	}

	void setEnterDateTime(long timeMillis) {	//����� ���� �ð� �����ϱ�
		enterDateTime = new GregorianCalendar();
		enterDateTime.setTimeInMillis(timeMillis);
	}

	int getPay() {	//�̿�ݾ� ��ȯ
		return pay;
	}

	void setPay(int pay) {	// ���ڰ����� ���� pay�� ����� �̿�ݾ����� ����
		this.pay = pay;
	}
	
	
}

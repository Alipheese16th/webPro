package volume;

//�������̽� - static final ����(���)�� �߻�޼ҵ�  (����: default�޼ҵ�, static�޼ҵ�)

public interface IVolume {
	
	public void volumeUp();
	public void volumeUp(int level);
	public void volumeDown();
	public void volumeDown(int level);
	
	public default void setMute(boolean mute) {	// default�޼ҵ�
		if(mute)
			System.out.println("����");
		else
			System.out.println("��������");
	};
	
	public static void changeBattery() {	// static �޼ҵ� 
		System.out.println("�������� ��ȯ�մϴ�");
	}
	
}

package Gym.Logic.Logic;

import Gym.Logic.Common.Input;
import 민국.LoginData;
import 민국.Trainer;
import 하성.Charge;
import 호영.Gym_Member;

//회원 가입을 전담함.
public class JoinManager {
    private static JoinManager instance = null;
    JoinManager(){}
    public static JoinManager getInstance()
    {
        if (instance == null)
        {
            instance = new JoinManager();
        }
        return instance;
    }

    public boolean tryTraninerJoin()
    {
        boolean result = false;
        //아이디 비밀번호 입력.
        ShowManager.getInstance().idInputform();
        String inputId = Input.stringScan();
        ShowManager.getInstance().pwInputform();
        String inputPw = Input.stringScan();

        //성별 입력
        ShowManager.getInstance().genderInputform();
        String inputGender = Input.stringScan();

        //나이 입력 ( 1~99 사이의 정수)
        ShowManager.getInstance().ageIntpuform();
        int inputAge = Input.intScan(1,99);

        //이름 입력
        ShowManager.getInstance().nameInputform();
        String inputName = Input.stringScan();

        //수상경력 입력
        ShowManager.getInstance().awardInputform();
        String inputAward = Input.stringScan();

        //새로 가입할 트레이너의 LoginData 생성.
        LoginData newLoginData = new LoginData(inputName, inputId, inputPw, inputGender,inputAge, LoginData.MEMBERTYPE.TRAINER);
        //Trainer(String login_Id, String login_Pw, String gender, String award, int age)
        Trainer joinTrainer = new Trainer(inputId, inputPw, inputGender,inputAward, inputAge);

        //Trainer Dao 를 이용하여 쿼리문 삽입. 동시에 1차적 검사.
        if(DAOManager.getInstance().gettDao().insertTrianerJoin(newLoginData))
        {
            int trNum = DAOManager.getInstance().gettDao().getTrainerNum(inputId);
            DAOManager.getInstance().gettDao().updateAward(trNum,inputAward);
            ShowManager.getInstance().successJoin();
            result = true;
        }
        else
        {
            ShowManager.getInstance().failedJoin();
            result = false;
        }

        return result;
    }

    public boolean memberJoin()
    {
        boolean result = false;
        //회원가입할 유저 아이디 비밀번호 입력.
        ShowManager.getInstance().idInputform();
        String inputId = Input.stringScan();
        ShowManager.getInstance().pwInputform();
        String inputPw = Input.stringScan();

        //회원의 성별 입력
        ShowManager.getInstance().genderInputform();
        String inputGender = Input.stringScan();

        //나이 입력 ( 1~99 사이의 정수)
        ShowManager.getInstance().ageIntpuform();
        int inputAge = Input.intScan(1,99);

        //이름 입력
        ShowManager.getInstance().nameInputform();
        String inputName = Input.stringScan();

        // Lesson 받게 될 정보를 입력받음. (Gym에 회원가입하는 회원은 보통 Lesson을 받기 위한 가입일 것이므로)
        for (Trainer t : DAOManager.getInstance().gettDao().findAll()){
            System.out.println(t.getTrainer_num() + "번: \t" + t.getName()+" 트레이너 님\t(" + t.getAward()+ ")");
        }

        ShowManager.getInstance().numberInputForm();
        int inputTrainerNumber = Input.intScan();

        // 모든 요금제 정보를 출력한다.
        for (Charge c : DAOManager.getInstance().getcDao().findAll()){
            System.out.println(c);
        }
        System.out.println("구매하신 요금제 번호를 입력해주세요. ");
        int inputChargeNumber = Input.intScan();
        int inputPtCount = DAOManager.getInstance().getcDao().findByCharge_num(inputChargeNumber).getPt_count();

        // 새로 가입할 멤버의 Gym_Member 객체 생성.
        Gym_Member toJoinMember = new Gym_Member(inputPtCount, inputId, inputPw, inputGender, inputAge, inputName, inputTrainerNumber, inputChargeNumber);

        result = DAOManager.getInstance().getmDao().insert(toJoinMember);
        if (result){
            ShowManager.getInstance().successJoin();
            System.out.println("로그인 페이지로 이동합니다.");
            LoginManager.getInstance().tryMemberLogin();
            // 성공했다면 성공 메시지 출력 후, 로그인 시도 페이지로 이동한다. 아이디와 비밀번호를 재차 입력하고 멤버 페이지로 이동 가능하다.
        }
        return result;
    }

    public Trainer createTrainer()
    {

        return null;
    }

}

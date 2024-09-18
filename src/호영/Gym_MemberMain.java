package 호영;

import Gym.Logic.Common.Gym;
import Gym.Logic.Common.Input;
import Gym.Logic.Logic.DAOManager;
import Gym.Logic.Logic.LoginManager;
import Gym.Logic.Logic.ShowManager;
import 민국.Trainer;
import 민국.TrainerDao;
import 진욱.Gym_LessonDao;
import 진욱.ReviewDao;
import 진욱.ReviewMain;
import 희진.Inbody;
import 희진.InbodyDao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class Gym_MemberMain {
    TrainerDao tDao = DAOManager.getInstance().gettDao();
    Gym_MemberDao mDao = DAOManager.getInstance().getmDao();
    InbodyDao iDao = DAOManager.getInstance().getiDao();
    ReviewDao rDao = DAOManager.getInstance().getrDao();
    Gym_LessonDao glDao = DAOManager.getInstance().getlDao();


    public void execute() {
        while (true) {
            ShowManager.getInstance().showMemberMenu();
            int choice = Input.intScan();
            switch (choice) {
                case 1:
                    updateMember();
                    break;
                case 2:
                    findAll();
                    break;
                case 3:
                    ReviewMain rm = new ReviewMain();
                    rm.reviewExecute();
                    break;
                case 4:
                    List<Inbody> memberInbodyData = iDao.findByMemberNum(LoginManager.getInstance().getCurrentMember().getMember_num());
                    System.out.println(LoginManager.getInstance().getCurrentMember().getName() + " 회원님의 인바디 데이터를 출력합니다.");
                    for (Inbody iData : memberInbodyData){
                        System.out.println(iData);
                    }
                    break;
                case 5:
                    deleteMember();
                    break;
                case 9:
                    System.out.println("로그아웃 후 프로그램을 종료합니다.");
                    LoginManager.getInstance().logOut();
                    Gym gym = new Gym();
                    gym.run();
                default:
                    System.out.println("올바른 선택이 아닙니다.");
                    break;
            }
        }
    }

    private void insertMember() { // Trainer 등이 회원을 추가할 때 사용할 메소드.
        Gym_Member member = new Gym_Member();
        System.out.println("회원 정보를 입력하세요:");

        System.out.print("PT 횟수: ");
        member.setPt_count(Input.intScan());

        System.out.print("등록일은 (yyyy-MM-dd HH:mm:ss) 형식으로 입력: ");
        member.setReg_date(Timestamp.valueOf(Input.stringScan()));

        System.out.print("만료일 (yyyy-MM-dd) 형식으로 입력: ");
        member.setExp_date(Date.valueOf(Input.stringScan()));

        System.out.print("로그인 ID: ");
        member.setLogin_id(Input.stringScan());

        System.out.print("로그인 비밀번호: ");
        member.setLogin_pw(Input.stringScan());

        System.out.print("성별: ");
        member.setGender(Input.stringScan());

        System.out.print("나이: ");
        member.setAge(Input.intScan());

        System.out.print("이름: ");
        member.setName(Input.stringScan());

        System.out.print("트레이너 번호: ");
        member.setTrainer_num(Input.intScan());

        System.out.print("회원권 번호: ");
        member.setCharge_num(Input.intScan());

        if (mDao.insert(member)) {
            System.out.println("회원이 성공적으로 추가되었습니다.");
        } else {
            System.out.println("회원 추가에 실패하였습니다.");
        }
    }

    private void findAll() {
        System.out.println("현재 등록된 전체 멤버 목록을 출력합니다.");
        List<Gym_Member> mList = mDao.findAll();
        for (Gym_Member gym_Member : mList) {
            System.out.println(gym_Member);
        }
        System.out.println("현재 Gym 총 인원수: " + mList.size() + "명");

    }

    private void updateMember() { // 기능은 3개 필요. 담당 트레이너 변경과 PT 횟수 변경, 비밀번호 변경.
        // 현재 로그인한 회원의 정보를 가져온다. 로그인 상태가 아니라면 회원을 찾을 수 없다는 메시지 출력 후 돌아간다.
        Gym_Member toFindMember = LoginManager.getInstance().getCurrentMember();
        if (toFindMember == null){
            System.out.println("로그인 상태가 아닙니다. 다시 시도해 주세요!");
            Gym gym = new Gym();
            gym.run();
        } else {
            System.out.println("수정하고 싶은 정보를 선택해주세요. 1. 담당 트레이너 변경 / 2. PT 횟수 변경 / 3. 비밀번호 변경");
            int method = Input.intScan();
            if (method == 1) {
                System.out.println("전체 트레이너를 출력합니다. 담당 변경을 원하는 트레이너 번호를 입력하세요. 현재 트레이너:" + tDao.findByIndex(toFindMember.getTrainer_num()).getName());
                for (Trainer t : DAOManager.getInstance().gettDao().findAll()) {
                    System.out.println(t);
                }
                int select = Input.intScan();
                toFindMember.setTrainer_num(select);
                mDao.update(toFindMember);
                System.out.println("담당 트레이너를 " + tDao.findByIndex(toFindMember.getTrainer_num()).getName() + "님으로 변경하셨습니다.");
            } else if (method == 2) {
                System.out.println("현재 로그인한 고객님의 PT 횟수 :" + mDao.findByMember_Num(toFindMember.getMember_num()).getPt_count() + "회");
                System.out.print("변경할 PT 횟수를 입력해주세요. ");
                int ptCount = Input.intScan();
                toFindMember.setPt_count(ptCount);
                mDao.update(toFindMember);
                System.out.println(toFindMember.getName() + "님의 PT 횟수를" + ptCount + "로 변경 완료하였습니다.");
            } else {
                System.out.print("새 비밀번호를 입력하세요! (현재: " + toFindMember.getLogin_pw() + "): ");
                String password = Input.stringScan();
                toFindMember.setLogin_pw(password);
                if (mDao.update(toFindMember)) {
                    System.out.println("회원 비밀번호가 성공적으로 변경되었습니다.");
                } else {
                    System.out.println("회원 비밀번호 변경 오류. 다시 시도해주세요.");
                }
            }
        }
    }

    private void deleteMember() {
        System.out.print("정말 탈퇴하시겠습니까? 1을 누르면 탈퇴를 진행합니다. 다른 버튼을 누르면 회원탈퇴가 취소됩니다.");
        int select = Input.intScan(); // 1
        switch (select){
            case 1:
                // 현재 로그인한 사람의 정보를 가져옴.
                int memberNum = LoginManager.getInstance().getCurrentMember().getMember_num();
                // 우선 gym_member 테이블의 하위 데이터인 review / inbody / class_list를 삭제한다.
                rDao.deleteReviewByMemberNum(memberNum);
                iDao.deleteByMemberNum(memberNum);
                glDao.deleteLessonByMemberNum(memberNum);

                if (mDao.deleteByMember_Num(memberNum)) {
                    System.out.println("회원이 성공적으로 삭제되었습니다.");
                    LoginManager.getInstance().logOut();
                    Gym gym = new Gym();
                    gym.run();
                } else {
                    System.out.println("회원 삭제에 실패하였습니다.");
                }
        }
    }
}

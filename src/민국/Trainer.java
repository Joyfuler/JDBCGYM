package 민국;

public class Trainer {
    private int trainer_num;
    private String award;

    private LoginData logindata = null;

    public Trainer() {
        logindata = new LoginData();
        logindata.setMemberType(LoginData.MEMBERTYPE.TRAINER);
    }

    public Trainer(int trainer_num, String name) {
        logindata = new LoginData();

        this.trainer_num = trainer_num;
        logindata.setName(name);
        logindata.setMemberType(LoginData.MEMBERTYPE.TRAINER);
    }



    public Trainer(String login_Id, String login_Pw) {
        logindata = new LoginData(login_Id, login_Pw);
        logindata.setMemberType(LoginData.MEMBERTYPE.TRAINER);
    }

    public Trainer(String login_Id, String login_Pw, String gender, String award, int age) {
        logindata = new LoginData();

        logindata.setLogin_id(login_Id);
        logindata.setLogin_pw(login_Pw);
        logindata.setGender(gender);
        logindata.setAge(age);
        logindata.setMemberType(LoginData.MEMBERTYPE.TRAINER);

        this.award = award;
    }

    public Trainer(int trainer_num, String login_Id, String login_Pw, String gender, String award, int age) {
        logindata = new LoginData();

        logindata.setLogin_id(login_Id);
        logindata.setLogin_pw(login_Pw);
        logindata.setGender(gender);
        logindata.setAge(age);
        logindata.setMemberType(LoginData.MEMBERTYPE.TRAINER);

        this.award = award;
        this.trainer_num = trainer_num;
    }

    public Trainer(Trainer tr)
    {
        this.trainer_num = tr.getTrainer_num();
        this.award = tr.getAward();
        this.logindata   = tr.getLoginData();
    }

    public int getTrainer_num() {
        return trainer_num;
    }

    public void setTrainer_num(int trainer_num) {
        this.trainer_num = trainer_num;
    }

    public void setLogin_Id(String login_Id) {
        this.logindata.setLogin_id(login_Id);
    }

    public String getLogin_Id() {
        return logindata.getLogin_id();
    }

    public String getLogin_Pw() {
        return logindata.getLogin_pw();
    }

    public void setLogin_Pw(String login_Pw) {
        this.logindata.setLogin_pw(login_Pw);
    }

    public String getGender() {
        return logindata.getGender();
    }

    public void setGender(String gender) {
        this.logindata.setGender(gender);
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public int getAge() {
        return logindata.getAge();
    }

    public void setAge(int age) {
        this.logindata.setAge(age);
    }

    public void setName(String name)
    {
        this.logindata.setName(name);
    }

    public String getName()
    {
        return this.logindata.getName();
    }

    public LoginData getLoginData()
    {
        return this.logindata;
    }
    public void setLoginData(LoginData logindata)
    {
        this.logindata = logindata;
    }


    public void setMemberType()
    {
        this.logindata.setMemberType(LoginData.MEMBERTYPE.TRAINER);
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "trainer_num=" + trainer_num +
                ", login_Id='" + logindata.getLogin_id() + '\'' +
                ", login_Pw='" + logindata.getLogin_pw() + '\'' +
                ", gender='" + logindata.getGender() + '\'' +
                ", award='" + award + '\'' +
                ", age=" + logindata.getAge() +
                ", name=" + logindata.getName() +
                '}';
    }

    public String showTrainerInfo(){
        return "트레이너 정보[" +
        "트레이너명 :" + logindata.getName() + "\t성별: " + logindata.getGender() + "\t수상경력 :" + award
        + "\t연령: " + logindata.getAge();
    }
}

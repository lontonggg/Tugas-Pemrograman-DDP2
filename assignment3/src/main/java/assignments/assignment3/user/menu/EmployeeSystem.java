package assignments.assignment3.user.menu;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Employee;
import assignments.assignment3.user.Member;

import static assignments.assignment3.nota.NotaManager.notaList;

public class EmployeeSystem extends SystemCLI {

    /**
     * Membuat object baru EmployeeSystem dan mendaftarkan Employee pada CuciCuci
     */
    public EmployeeSystem() {
        memberList = new Member[]{
                new Employee("Dek Depe", "akuDDP"),
                new Employee("Depram", "musiktualembut"),
                new Employee("Lita Duo", "gitCommitPush"),
                new Employee("Ivan Hoshimachi", "SuamiSahSuisei"),
        };
    }

    /**
     * Memproses pilihan dari employee yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice, Member member) {
        boolean logout = false;
        if(choice == 1){
            boolean servicesDone = false;
            System.out.printf("Stand back! %s beginning to nyuci!\n", member.getNama());
            for(Nota nota: NotaManager.notaList){
                for(LaundryService service: nota.getServices()){
                    servicesDone = service.isDone();
                    if(service.isDone() == false){
                        System.out.printf("Nota %d : %s\n", nota.getId(), service.doWork());
                        break;
                    }
                }
                if(servicesDone){
                    System.out.printf("Nota %d : %s\n", nota.getId(), "Sudah selesai.");
                }
            }
            
        }
        else if(choice == 2){
            for(Nota nota: notaList){
                System.out.printf("Nota %d : %s\n", nota.getId(), nota.getNotaStatus());
            }
        }
        else if(choice == 3){
            logout = true;
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Employee.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. It's nyuci time");
        System.out.println("2. Display List Nota");
        System.out.println("3. Logout");
    }
}
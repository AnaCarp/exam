import architecture.mvc.ViewMVC;
import architecture.mvp.Presenter;
import architecture.mvp.ViewMVP;
import architecture.mvc.Controller;
import architecture.mvvm.ViewMVVM;
import architecture.mvvm.ViewModel;
import model.DatabaseManager;

public class Main {
    public static void main(String[] args) {

        //----------------------------------MVC
//        // Inițializare bază de date
//        DatabaseManager.initializeDatabase();
//
//        // Creare instanță View
//        ViewMVC view_mvc = new ViewMVC();
//
//        // Creare instanță Controller
//        Controller controller = new Controller(view_mvc);
//
//        // Pornire aplicație
//        controller.start();

        //----------------------------------MVP
        // Inițializare bază de date
        DatabaseManager.initializeDatabase();

        // Creare instanță View
        ViewMVP view_mvp = new ViewMVP();

        // Creare instanță Presenter
        Presenter presenter = new Presenter(view_mvp);

        // Pornire aplicație
        presenter.start();

        //----------------------------------MVVM
        DatabaseManager.initializeDatabase();

        // Initialize ViewModel
        ViewModel viewModel = new ViewModel();

        // Initialize View with ViewModel
        ViewMVVM view = new ViewMVVM(viewModel);

        // Start the View, which will interact with ViewModel
        view.start();

    }
}
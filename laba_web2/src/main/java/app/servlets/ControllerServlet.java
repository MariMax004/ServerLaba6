package app.servlets;

import app.model.Model;
import app.pointData.Point;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class ControllerServlet extends HttpServlet {
    Double parse(String str){
        return Double.parseDouble(str.replace(',','.'));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/main_page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     try {
            Double x = parse(req.getParameter("X"));
            Double y = parse(req.getParameter("Y"));
            Double r = parse(req.getParameter("InputR"));
            Point point = new Point();
            point.setX(x);
            point.setY(y);
            point.setR(r);
            point.setRes(checkPoint(x,y,r));
            Model model = new Model();
            model.setPoint(point);
            getServletContext().getRequestDispatcher("/areaCheckServlet").forward(req, resp);
        }catch (NumberFormatException | NullPointerException exception ){
            getServletContext().getRequestDispatcher("/main_page.jsp").forward(req, resp);
        }catch (Exception e){
            PrintWriter writer = resp.getWriter();
            writer.write("error");
            writer.close();
        }

    }
    public String checkPoint(double x, double y, double r){
        if((x*x + y*y <= r*r && x <= 0 && y <= 0) ||
                (y+x <= r && x>=0 && y>=0) ||
                (x<=0 && y>=0 && y<=r && x>=-r)){
            return   "Входит";
        }else{
            return "Не входит";
        }
    }
}

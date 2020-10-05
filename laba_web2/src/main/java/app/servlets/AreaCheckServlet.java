package app.servlets;


import app.model.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/areaCheckServlet")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String answer = "<head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\" />\n" +
                "</head>\n" +
                "    <body>\n" +
                "        <table class=\"table\">\n" +
                "          <thead>\n" +
                "          <td>X</td>\n" +
                "          <td>Y</td>\n" +
                "          <td>R</td>\n" +
                "          <td>RESULT</td>\n" +
                "          </thead>\n" +
                "          <tbody id=\"table_out\">";

        answer += Model.points.get(Model.points.size()-1).getRes();

        answer += "</tbody></table><button class=\"submit-button\" onclick=\"location.href='http://0.0.0.0:2634/laba_web2_war_exploded/'\">" +
                "Назад</button></body>" +
                "<canvas id=\"canvas\" width=\"400\" height=\"400\"></canvas>";
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(answer);
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String answer = "<head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <title>Laba2</title>\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\" />\n" +
                "</head>\n" +
                "    <body>\n" +
                "        <table class=\"table\">\n" +
                "          <thead>\n" +
                "          <td>X</td>\n" +
                "          <td>Y</td>\n" +
                "          <td>R</td>\n" +
                "          <td>RESULT</td>\n" +
                "          </thead>\n" +
                "          <tbody id=\"table_out\">";

        answer += Model.points.get(Model.points.size()-1).getRes();

        answer += "</tbody></table><button class=\"submit-button\" onclick=\"location.href='http://0.0.0.0:2634/laba_web2_war_exploded/'\">" +
                "Назад</button></body>" +
                "<canvas id=\"canvas\" width=\"400\" height=\"400\"></canvas>";
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(answer);
        out.close();
    }
}

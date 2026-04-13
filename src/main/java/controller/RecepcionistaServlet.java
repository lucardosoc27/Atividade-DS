package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "RecepcionistaServlet", urlPatterns = {"/RecepcionistaServlet"})
public class RecepcionistaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Pega o nome do cliente no index
        String nome = request.getParameter("nomeCliente");

        // Abre a sessao do cliente atual
        HttpSession sessao = request.getSession();

        // Setta o nome digitado pelo cliente, na sessao dele
        sessao.setAttribute("nomeDoCliente", nome);

        // Manda o cliente para a pagina do cardapio
        response.sendRedirect("cardapio.html");
    }
}
package controller;

import model.Produto;
import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/PedidoServlet")
public class PedidoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession sessao = request.getSession();
        
        
        ArrayList<Produto> carrinho = (ArrayList<Produto>) sessao.getAttribute("meuCarrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
        }

        String opcaoClicada = request.getParameter("opcao");
        int opcao = 0;

        try {
            if (opcaoClicada != null) {
                opcao = Integer.parseInt(opcaoClicada);
            }
        } catch (Exception erro) {
            opcao = 0;
        }

        switch (opcao) {
            // Hamburgue
            case 1 -> carrinho.add(new Produto("X-Salada", 15.0));
            case 2 -> carrinho.add(new Produto("X-Bacon", 15.0));
            case 3 -> carrinho.add(new Produto("X-Frango", 15.0));
            case 4 -> carrinho.add(new Produto("Vegetariano", 15.0));
            
            // Pizzas
            case 5 -> carrinho.add(new Produto("Pizza Calabresa", 25.0));
            case 6 -> carrinho.add(new Produto("Pizza Frango com Catupiry", 25.0));
            case 7 -> carrinho.add(new Produto("Pizza Portuguesa", 25.0));
            case 8 -> carrinho.add(new Produto("Pizza Vegetariana", 25.0));
            
            // Batata 
            case 9 -> carrinho.add(new Produto("Batata Tradicional", 10.0));
            case 10 -> carrinho.add(new Produto("Batata com Cheddar e Bacon", 10.0));
            
            // Bebida
            case 11 -> carrinho.add(new Produto("Guaraná Antarctica", 5.0));
            case 12 -> carrinho.add(new Produto("Coca Cola", 5.0));
            case 13 -> carrinho.add(new Produto("Suco de Laranja", 5.0));
            case 14 -> carrinho.add(new Produto("Chá Gelado Mate Leão", 5.0));
        }

        // Atualiza a sessão do usuario com o carrinho novo
        sessao.setAttribute("meuCarrinho", carrinho);
        
        // Redireciona de volta pra a tela inicial 
        response.sendRedirect("cardapio.html");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession sessao = request.getSession();
        
        
        ArrayList<Produto> carrinho = (ArrayList<Produto>) sessao.getAttribute("meuCarrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
        }

        // Pega o nome do cliente, e se não tiver, vira Visitante
        String nomeCliente = (String) sessao.getAttribute("nomeDoCliente");
        if (nomeCliente == null) {
            nomeCliente = "Visitante";
        }

        // Calcula o total da compra
        double total = 0;
        for (Produto item : carrinho) {
            total += item.preco();
        }

        // Constroi a nota fiscal
        
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"cliente\": \"").append(nomeCliente).append("\", ");
        json.append("\"total\": ").append(total).append(", ");
        json.append("\"itens\": [");
        
        for (int i = 0; i < carrinho.size(); i++) {
            Produto p = carrinho.get(i);
            json.append("{");
            json.append("\"nome\": \"").append(p.nome()).append("\", ");
            json.append("\"preco\": ").append(p.preco());
            json.append("}");
            
            
            if (i < carrinho.size() - 1) {
                json.append(", ");
            }
        }
        json.append("]");
        json.append("}");

        // Limpa o carrinho para o proximo cliente
        sessao.removeAttribute("meuCarrinho");

        // Avisa o navegador que o arquivo é JSON e não um HTML
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        response.getWriter().write(json.toString());
    }
}
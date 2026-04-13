<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Produto" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cupom Fiscal</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="tela-centralizada">
    <div class="cupom">
        <h2>Seu Pedido</h2>
        
        <div class="cliente-nome">
            Cliente: <%= (session.getAttribute("nomeDoCliente") != null) ? session.getAttribute("nomeDoCliente") : "Visitante" %>
        </div>
        
        <div class="divisor"></div>
        
        <% 
            ArrayList<Produto> lista = (ArrayList<Produto>) request.getAttribute("listaItens");
            Double total = (Double) request.getAttribute("valorTotal");

            if (lista != null && !lista.isEmpty()) {
                for (Produto p : lista) { 
        %>
                    <div class="item">
                        <span><%= p.nome() %></span>
                        <span>R$ <%= String.format("%.2f", p.preco()) %></span>
                    </div>
        <% 
                } 
            } else { 
        %>
                <p style="text-align: center;">Nenhum item selecionado.</p>
        <% 
            } 
        %>

        <div class="total">
            Total: R$ <%= (total != null) ? String.format("%.2f", total) : "0,00" %>
        </div>

        <a href="index.html" class="botao-voltar">Atender Novo Cliente</a>
    </div>

</body>
</html>
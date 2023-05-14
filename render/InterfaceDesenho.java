package render;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import java.io.InputStream;

import javax.imageio.ImageIO;

import entidade.No;

import estrutura.Logica;

/**
 * Classe responsável por todo o trabalho de desenho do jogo
 */
public class InterfaceDesenho{
   PainelJogo painelJogo;
   public BufferedImage rosto_cima_padrao, rosto_baixo_padrao, rosto_esquerda_padrao, rosto_direita_padrao, corpo_cobrinha_padrao;//roxo
   public BufferedImage rosto_cima_2, rosto_baixo_2, rosto_esquerda_2, rosto_direita_2, corpo_cobrinha_2;//verde
   public BufferedImage rosto_cima_3, rosto_baixo_3, rosto_esquerda_3, rosto_direita_3, corpo_cobrinha_3;//cinza
   public BufferedImage rosto_cima_4, rosto_baixo_4, rosto_esquerda_4, rosto_direita_4, corpo_cobrinha_4;//rosa
   public BufferedImage rosto_cima_5, rosto_baixo_5, rosto_esquerda_5, rosto_direita_5, corpo_cobrinha_5;//amarelo

   //variaveis de desenho na execucao do jogo
   public BufferedImage rosto_cima_atual, rosto_baixo_atual, rosto_esquerda_atual, rosto_direita_atual, corpo_atual, imagem_desenho_atual;

   public BufferedImage rostoExemplo, corpoExemplo;// imagens usadas na tela de configuração
   public BufferedImage imagemCobrinha, imagemFruta;//imagens usadas nos menus

   final Color corTextoPadrao = new Color(120, 100, 190);
   final Color corTextoSelecionadoPadrao = new Color(170, 170, 230);
   final Color corSombraPadrao = new Color(50, 40, 70);

   final Color corTexto2 = new Color(70, 180, 70);
   final Color corTextoSelecionado2 = new Color(100, 240, 100);
   final Color corSombra2 = new Color(40, 100, 40);

   final Color corTexto3 = new Color(160, 160, 160);
   final Color corTextoSelecionado3 = new Color(255, 250, 255);
   final Color corSombra3 = new Color(70, 70, 70);

   Color corTexto, corTextoSelecionado, corSombra;

   //evitar instanciar valores no meio da execuçao 
   int contador = 0;
   int tamanhoFonte;
   String texto, textoAuxiliar, textoDescicaoOpcao;
   int alturaTexto, textoX, textoY;
   int relevoSombra = 1;
   int desenhoX, desenhoY;
   Font fonteJogo;
   int fonteTexto = Font.BOLD;
   int comprimentoTextoCentralizado;

   final int tamanhoFonteTitulo = 12;
   final int tamanhoFonteMenus = 7;
   final int tamanhoFonteRodape = 5;

   final int alinhamentoTextoVertical;

   //EVITAR PASSAR EXPRESSOES PELAS FUNCOES,
   //PASSAR VALORES JA DEFINIDOS    PARA FUNCOES
   //EVITAR INSTANCIAR ELEMENTOS 
   InterfaceDesenho(PainelJogo painelJogo){
      this.painelJogo = painelJogo;

      corTexto = corTextoPadrao;
      corSombra = corSombraPadrao;
      corTextoSelecionado = corTextoSelecionadoPadrao;

      alinhamentoTextoVertical = painelJogo.tamanhoBloco * 3;

      getFonte("Abalone Smile");
      getImagens(painelJogo);
   }

   /**
    * Recupera o arquivo de fonte referente aos arquivos salvo na pasta do jogo
    * @param nomeFonte Nome da fonte(extensão .ttf) equivalente aos arquivos salvos na pasta do jogo
    */
   public void getFonte(String nomeFonte){
      try {
         InputStream inputStream = getClass().getResourceAsStream("/fonte/" + nomeFonte +".ttf");
         fonteJogo = Font.createFont(Font.TRUETYPE_FONT, inputStream);
      }catch(Exception e){
      }      
   }

   /**
    * Recupera todas as imagens necessárias dos arquivos de imagens do jogo
    * @param painelJogo Painel em que está sendo renderizado o jogo
    */
   public void getImagens(PainelJogo painelJogo){
      try{
         Logica logica = new Logica();
         //tema padrao
         rosto_cima_padrao = setupImagem(logica, "cobrinha_rosto_cima", painelJogo.tamanhoBloco);
         rosto_baixo_padrao = setupImagem(logica, "cobrinha_rosto_baixo", painelJogo.tamanhoBloco);
         rosto_esquerda_padrao = setupImagem(logica, "cobrinha_rosto_esquerda", painelJogo.tamanhoBloco);
         rosto_direita_padrao = setupImagem(logica, "cobrinha_rosto_direita", painelJogo.tamanhoBloco);
         corpo_cobrinha_padrao = setupImagem(logica, "cobrinha_corpo", painelJogo.tamanhoBloco);       

         //tema verde
         rosto_cima_2 = setupImagem(logica, "cobrinha_rosto_cima_verde", painelJogo.tamanhoBloco);
         rosto_baixo_2 = setupImagem(logica, "cobrinha_rosto_baixo_verde", painelJogo.tamanhoBloco);
         rosto_esquerda_2 = setupImagem(logica, "cobrinha_rosto_esquerda_verde", painelJogo.tamanhoBloco);
         rosto_direita_2 = setupImagem(logica, "cobrinha_rosto_direita_verde", painelJogo.tamanhoBloco);
         corpo_cobrinha_2 = setupImagem(logica, "cobrinha_corpo_verde", painelJogo.tamanhoBloco);

         //tema cinza
         rosto_cima_3 = setupImagem(logica, "cobrinha_rosto_cima_cinza", painelJogo.tamanhoBloco);
         rosto_baixo_3 = setupImagem(logica, "cobrinha_rosto_baixo_cinza", painelJogo.tamanhoBloco);
         rosto_esquerda_3 = setupImagem(logica, "cobrinha_rosto_esquerda_cinza", painelJogo.tamanhoBloco);
         rosto_direita_3 = setupImagem(logica, "cobrinha_rosto_direita_cinza", painelJogo.tamanhoBloco);
         corpo_cobrinha_3 = setupImagem(logica, "cobrinha_corpo_cinza", painelJogo.tamanhoBloco);

         //tema rosa
         rosto_cima_4 = setupImagem(logica, "cobrinha_rosto_cima_rosa", painelJogo.tamanhoBloco);
         rosto_baixo_4 = setupImagem(logica, "cobrinha_rosto_baixo_rosa", painelJogo.tamanhoBloco);
         rosto_esquerda_4 = setupImagem(logica, "cobrinha_rosto_esquerda_rosa", painelJogo.tamanhoBloco);
         rosto_direita_4 = setupImagem(logica, "cobrinha_rosto_direita_rosa", painelJogo.tamanhoBloco);
         corpo_cobrinha_4 = setupImagem(logica, "cobrinha_corpo_rosa", painelJogo.tamanhoBloco);

         //tema amarelo
         rosto_cima_5 = setupImagem(logica, "cobrinha_rosto_cima_amarelo", painelJogo.tamanhoBloco);
         rosto_baixo_5 = setupImagem(logica, "cobrinha_rosto_baixo_amarelo", painelJogo.tamanhoBloco);
         rosto_esquerda_5 = setupImagem(logica, "cobrinha_rosto_esquerda_amarelo", painelJogo.tamanhoBloco);
         rosto_direita_5 = setupImagem(logica, "cobrinha_rosto_direita_amarelo", painelJogo.tamanhoBloco);
         corpo_cobrinha_5 = setupImagem(logica, "cobrinha_corpo_amarelo", painelJogo.tamanhoBloco);

         //uso nas opções dos menus
         imagemCobrinha = setupImagem(logica, "cobrinha_rosto_baixo", painelJogo.tamanhoBloco);
         imagemFruta = setupImagem(logica, "fruta", painelJogo.tamanhoBloco);
      }catch(Exception e){
         e.printStackTrace();
      }
   }

   /**
    * De acordo com o tamanho da imagem original e a escala do jogo.
    * retorna a imagem com tamanho proporcional ao escalamento do painel 
    * @param nomeImagem Nome do arquivo da imagem, sem o caminho e sem extensão
    * @param tamanhoFinal Valor do scaling do painel para o tamanho final
    * @return Imagem convertida para a proporção real da janela
    */
   private BufferedImage setupImagem(Logica logica, String nomeImagem, int tamanhoFinal){
      //escala a imagem antes do jogo iniciar para
      //não perder tempo escalando durante a execucao do jogo
      BufferedImage imagem = null;
      try{
         String extensao = ".png";
         imagem = ImageIO.read(getClass().getResourceAsStream("/images/" + nomeImagem + extensao));
         imagem = logica.imagemScalada(imagem, tamanhoFinal, tamanhoFinal);
         
      }catch(Exception e){
         System.out.println(e);
      }
      return imagem;
   }

   /**
    * Ajustes simples para configuração do texto
    * @param g2d Graficos 2D usados na renderização do jogo 
    * @param fonte Fonte que será utilizada
    * @param corTexto Cor em que o texto será desenhado
    * @param relevo Nível de desvio do texto com o texto auxiliar que será utilizado como sombra
    */
   private void configurarTexto(Graphics2D g2d, Font fonte, Color corTexto, int relevo){
      g2d.setFont(fonte);
      g2d.setColor(corTexto);      
      relevoSombra = relevo;
   }

   /**
    * Desenha todo o corpo da cobrinha, incluido a cabeça em relação
    * à direção em que ela está indo, faz distinção do desenho se é a cobrinha do menu ou
    * a do jogo
    * @param g2d Graficos 2D usados na renderização do jogo 
    * @param direcao Direção da cobrinha
    * @param cobrinha Cobrinha que será desenhada
    * @param cobrinha_menu Qual cobrinha será desenhada
    */
   public void desenharCobrinha(Graphics2D g2, ArrayList<No> cobrinha, String direcao, boolean cobrinha_menu){
      //desenhar ao contrario pro rosto nao ser sobreposto e ficar visivel
      if(cobrinha_menu){
         for(contador = cobrinha.size()-1; contador >= 0; contador--){
            g2.drawImage(cobrinha.get(contador).icone, cobrinha.get(contador).x, cobrinha.get(contador).y, null);
         }
      }else{
         for(contador = cobrinha.size()-1; contador >= 0; contador --){
            if(contador == 0){           
               switch(direcao){
                  case "cima":
                     imagem_desenho_atual = rosto_cima_atual;
                  break;
         
                  case "esquerda":
                     imagem_desenho_atual = rosto_esquerda_atual;
                  break;
         
                  case "baixo":
                     imagem_desenho_atual = rosto_baixo_atual;
                  break;
         
                  case "direita":
                     imagem_desenho_atual = rosto_direita_atual;
                  break;
               }
            }else{
               imagem_desenho_atual = cobrinha.get(contador).icone; 
            }        
            g2.drawImage(imagem_desenho_atual, cobrinha.get(contador).x, cobrinha.get(contador).y, null);
         }
      }
   }

   /**
    * Desenha todas as partes relativas à tela de início do jogo, como título do jogo
    * os nomes dos menus e as imagens que serão usadas
    * @param g2d Graficos 2D usados na renderização do jogo 
    */
   public void telaComecoJogo(Graphics2D g2d){
      //configurando o texto
      configurarTexto(g2d, fonteJogo, corTexto, 3);
      alturaTexto = painelJogo.larguraTela/2;

      //titulo jogo
      tamanhoFonte = painelJogo.scale * tamanhoFonteTitulo;
      g2d.setFont(g2d.getFont().deriveFont(fonteTexto, tamanhoFonte));
      texto = "Jogo da Cobrinha";
      textoX = textoCentralizado(g2d, texto);
      textoY = painelJogo.tamanhoBloco * 5;
      desenharTextoComSombra(g2d,texto, textoX, textoY, corTexto, corSombra);

      //opcao novo jogo
      tamanhoFonte = painelJogo.scale * tamanhoFonteMenus;
      g2d.setFont(g2d.getFont().deriveFont(fonteTexto, tamanhoFonte));
      texto = "Novo jogo";
      textoX = textoCentralizado(g2d, texto);
      textoY = (painelJogo.alturaTela/2) + painelJogo.tamanhoBloco;
      if(!(painelJogo.numeroComando == 1)){
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTexto, corSombra);
      }else{
         desenharIcone(g2d, imagemCobrinha, textoX, textoY);
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTextoSelecionado, corSombra);         
      }

      //opcao controles
      g2d.setFont(g2d.getFont().deriveFont(fonteTexto, tamanhoFonte));
      texto = "Configurações";
      textoX = textoCentralizado(g2d, texto);
      textoY += painelJogo.tamanhoBloco * 2;
      if(!(painelJogo.numeroComando == 2)){
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTexto, corSombra);
      }else{
         desenharIcone(g2d, imagemCobrinha, textoX, textoY);
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTextoSelecionado, corSombra);         
      }

      //opcao sair
      g2d.setFont(g2d.getFont().deriveFont(fonteTexto, tamanhoFonte));
      texto = "Sair";
      textoX = textoCentralizado(g2d, texto);
      textoY += painelJogo.tamanhoBloco * 2;
      if(!(painelJogo.numeroComando == 3)){
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTexto, corSombra);
      }else{
         desenharIcone(g2d, imagemFruta, textoX, textoY);
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTextoSelecionado, corSombra);         
      }

      //criador
      tamanhoFonte = painelJogo.scale * tamanhoFonteRodape;
      g2d.setFont(g2d.getFont().deriveFont(fonteTexto, tamanhoFonte));
      texto = "Thiago";
      textoX = painelJogo.tamanhoBloco;
      textoY = (painelJogo.alturaTela) - painelJogo.tamanhoBloco;
      desenharTextoComSombra(g2d, texto, textoX, textoY, corTexto, corSombra);

      textoX = painelJogo.larguraTela - (painelJogo.tamanho);
      desenharIcone(g2d, imagemFruta, textoX, textoY);
   }

   /**
    * Desenha todas as partes relativas à tela de configuração do jogo, como o título da janela
    * os textos dos menus e as imagens que serão usadas
    * @param g2d Graficos 2D usados na renderização do jogo 
    */
   public void telaConfiguracao(Graphics2D g2d){
      //configurando o texto
      configurarTexto(g2d, fonteJogo, corTexto, 3);
      alturaTexto = painelJogo.larguraTela/3;

      //titulo Configuraçoes
      tamanhoFonte = painelJogo.scale * tamanhoFonteTitulo;
      g2d.setFont(g2d.getFont().deriveFont(fonteTexto, tamanhoFonte));
      texto = "Configurações";
      textoX = textoCentralizado(g2d, texto);
      textoY = alturaTexto/3;  
      desenharTextoComSombra(g2d, texto, textoX, textoY, corTexto, corSombra); 
      
      //opçoes de configuraçao
      //atravessar bordas - numeroOpcao1
      tamanhoFonte = painelJogo.scale * tamanhoFonteMenus;
      g2d.setFont(g2d.getFont().deriveFont(fonteTexto, tamanhoFonte));
      texto = "Ignorar bordas";
      textoX = alinhamentoTextoVertical;
      textoY = (painelJogo.alturaTela/2) - (painelJogo.tamanhoBloco*6);      
      
      if(painelJogo.modoIgnorarBorda){
         textoAuxiliar = "Ligado";       
      }else{
         textoAuxiliar = "Desligado"; 
      }
      if(!(painelJogo.numeroComando == 1)){
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTexto, corSombra);
         textoX = painelJogo.larguraTela - (painelJogo.tamanhoBloco * (tamanhoFonteMenus + 2));
         desenharTextoComSombra(g2d, textoAuxiliar, textoX, textoY, corTexto, corSombra);
      }else{
         desenharIcone(g2d, imagemCobrinha, textoX, textoY);
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTextoSelecionado, corSombra);
         textoX = painelJogo.larguraTela - (painelJogo.tamanhoBloco * (tamanhoFonteMenus + 2));
         desenharTextoComSombra(g2d, textoAuxiliar, textoX, textoY, corTextoSelecionado, corSombra);
      }

      //mudar cor tema - numeroOpcao2
      texto = "Cor tema";
      textoX = alinhamentoTextoVertical;
      textoY += painelJogo.tamanhoBloco * 3;

      textoAuxiliar = "Tema " + String.valueOf(painelJogo.temaCoresAtual);
      if(!(painelJogo.numeroComando == 2)){
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTexto, corSombra);
         textoX = painelJogo.larguraTela - (painelJogo.tamanhoBloco * (tamanhoFonteMenus + 2));
         desenharTextoComSombra(g2d, textoAuxiliar, textoX, textoY, corTexto, corSombra);
      }else{
         desenharIcone(g2d, imagemCobrinha, textoX, textoY);
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTextoSelecionado, corSombra);
         textoX = painelJogo.larguraTela - (painelJogo.tamanhoBloco * (tamanhoFonteMenus + 2));
         desenharTextoComSombra(g2d, textoAuxiliar, textoX, textoY, corTextoSelecionado, corSombra);
      }

      //alterar velocidade - numeroOpcao3
      texto = "Tema cobrinha";
      textoX = alinhamentoTextoVertical;
      textoY += painelJogo.tamanhoBloco*3;

      //nao achei outra solução entao ele fica isolado do corpoExemplo por enquanto
      rostoExemplo = rosto_direita_atual;
      if(painelJogo.temaCobrinhaAtual == 6) corpo_atual = painelJogo.logica.gerarCorpoAleatorio(painelJogo, this);
      else corpoExemplo = corpo_atual;

      desenhoX = painelJogo.larguraTela - (painelJogo.tamanhoBloco*6);
      desenharIcone(g2d, corpoExemplo, desenhoX, textoY);

      desenhoX += (painelJogo.tamanhoBloco*3);
      desenharIcone(g2d, rostoExemplo, desenhoX, textoY);     

      if(!(painelJogo.numeroComando == 3)){
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTexto, corSombra);
      }else{
         desenharIcone(g2d, imagemCobrinha, textoX, textoY);
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTextoSelecionado, corSombra);
      }
      
      //descriçao das opçoes---------------------------
      if(painelJogo.numeroComando == 1){// ignorar bordas
         textoDescicaoOpcao = "Ignora a colisão com as bordas, fazendo \n a cobrinha ir para a outra extremidade";
      }else if(painelJogo.numeroComando == 2){//cor tema
         textoDescicaoOpcao = "Altera a cor temática do jogo";
      }else if(painelJogo.numeroComando == 3 && painelJogo.corTemaAleatorio){// cor cobrinha
         textoDescicaoOpcao = "A cor gerada será aleatória!";
      }else if(painelJogo.numeroComando == 3 && !painelJogo.corTemaAleatorio){// cor cobrinha
         textoDescicaoOpcao = "Altera a cor temática da cobrinha!";
      }

      tamanhoFonte = painelJogo.scale * (tamanhoFonteRodape);
      g2d.setFont(g2d.getFont().deriveFont(fonteTexto, tamanhoFonte));
      textoY += (painelJogo.tamanhoBloco*4);
      //dividir texto grande em duas varias linhas
      if(painelJogo.numeroComando == 1 || painelJogo.numeroComando == 3){
         for(String linha : textoDescicaoOpcao.split("\n")){
            textoX = textoCentralizado(g2d, linha);
            desenharTextoComSombra(g2d, linha, textoX, textoY, corTexto, corSombra);            
            textoY += (painelJogo.tamanhoBloco*2);
         }
      }else{
         textoX = textoCentralizado(g2d, textoDescicaoOpcao);
         desenharTextoComSombra(g2d, textoDescicaoOpcao, textoX, textoY, corTexto, corSombra);
      }

      //texto mudar opcoes e esc
      tamanhoFonte = painelJogo.scale * tamanhoFonteRodape;
      g2d.setFont(g2d.getFont().deriveFont(fonteTexto, tamanhoFonte));
      texto = "Mudar opções com A ou D";
      textoX = painelJogo.tamanhoBloco;
      textoY = painelJogo.alturaTela - (painelJogo.tamanhoBloco * 2);   
      desenharTextoComSombra(g2d, texto, textoX, textoY, corTexto, corSombra);

      texto = "Esc - Voltar ao menu";
      textoX = 0 + painelJogo.tamanhoBloco;
      textoY = painelJogo.alturaTela - (painelJogo.tamanhoBloco);   
      desenharTextoComSombra(g2d, texto, textoX, textoY, corTexto, corSombra);

      desenhoX = painelJogo.larguraTela - (painelJogo.tamanho);
      desenhoY = painelJogo.alturaTela - painelJogo.tamanhoBloco;
      desenharIcone(g2d, imagemFruta, desenhoX, desenhoY);      
   }

   /**
    * Desenha todas as partes relativas à tela de execução do jogo em si, como o placar de pontos
    * a cobrinha e todas as suas partes e a fruta
    * @param g2d Graficos 2D usados na renderização do jogo 
    * @param painelJogo Painel em que está sendo renderizado o jogo
    */
   public void telaJogo(Graphics2D g2d, PainelJogo painelJogo){
      //false porque a cobrinha desenhada não é a do menu
      desenharCobrinha(g2d, painelJogo.cobrinha, painelJogo.direcao, false);
      g2d.drawImage(imagemFruta, painelJogo.fruta.x, painelJogo.fruta.y, painelJogo.tamanhoBloco, painelJogo.tamanhoBloco, null);

      //texto de pontos
      configurarTexto(g2d, fonteJogo, corTexto, relevoSombra);
      g2d.setFont(g2d.getFont().deriveFont(fonteTexto, tamanhoFonte));
      String texto = "Pontos " + painelJogo.pontos;
      textoX = textoCentralizado(g2d, texto);
      textoY = painelJogo.tamanhoBloco + (painelJogo.tamanhoBloco/2);      
      g2d.drawString(texto, textoX, textoY);
   }


   /**
    * Desenha tudo relativo à tela final do jogo, após a cobrinha morrer por qualquer motivo que seja,
    * inclui os textos e as imagens necessárias
    * @param g2d Graficos 2D usados na renderização do jogo 
    * @param causa Variável que será usada para mostrar a mensagem na tela de fim de jogo
    */
   public void telaFimDeJogo(Graphics2D g2d, String causa){
      //configurando o texto
      configurarTexto(g2d, fonteJogo, corTexto, 3);
      tamanhoFonte = painelJogo.scale * tamanhoFonteTitulo;
      alturaTexto = painelJogo.larguraTela/3;
      
      //texto fim de jogo   
      tamanhoFonte = painelJogo.scale * tamanhoFonteTitulo;
      g2d.setFont(g2d.getFont().deriveFont(fonteTexto, tamanhoFonte));
      texto = causa;
      textoX = textoCentralizado(g2d, texto);
      textoY = alturaTexto/2;  
      desenharTextoComSombra(g2d, texto, textoX, textoY, corTexto, corSombra);
      
      //texto pontos
      tamanhoFonte = painelJogo.scale * tamanhoFonteMenus;
      g2d.setFont(g2d.getFont().deriveFont(fonteTexto, tamanhoFonte));
      texto = "Sua pontuação: " + painelJogo.pontos;
      textoX = textoCentralizado(g2d, texto);
      textoY = painelJogo.larguraTela/2;
      desenharTextoComSombra(g2d, texto, textoX, textoY, corTexto, corSombra);

      //desenhar opcoes
      tamanhoFonte = painelJogo.scale * tamanhoFonteRodape;
      texto = "Iniciar novo Jogo";
      textoX = textoCentralizado(g2d, texto);   
      textoY = painelJogo.alturaTela - (painelJogo.tamanhoBloco*7);  
      if(!(painelJogo.numeroComando == 1)){//opcao novo jogo
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTexto, corSombra);
      }else{
         desenharIcone(g2d, imagemCobrinha, textoX, textoY);
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTextoSelecionado, corSombra);         
      }
      
      texto = "Voltar ao menu";
      textoX = textoCentralizado(g2d, texto);
      textoY += painelJogo.tamanhoBloco*2;
      if(!(painelJogo.numeroComando == 2)){// opcao voltar ao menu
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTexto, corSombra);
      }else{
         desenharIcone(g2d, imagemCobrinha, textoX, textoY);
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTextoSelecionado, corSombra);
      }
   
      texto = "Sair";
      textoX = textoCentralizado(g2d, texto);
      textoY += painelJogo.tamanhoBloco*2;
      if(!(painelJogo.numeroComando == 3)){// opcao sair
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTexto, corSombra);
      }else{
         desenharIcone(g2d, imagemFruta, textoX, textoY);
         desenharTextoComSombra(g2d, texto, textoX, textoY, corTextoSelecionado, corSombra);
      } 
   }

   /**
    * Calula de acordo com o tamanho do texto, a posição ideal para que ele fique centralizado
    * em relação ao tamanho da tela
    * @param g2d Graficos 2D usados na renderização do jogo 
    * @param texto Mensagem que será utilizada
    * @return retorna o valor horizontal
    */
   private int textoCentralizado(Graphics2D g2d, String texto){
      comprimentoTextoCentralizado = (int) g2d.getFontMetrics().getStringBounds(texto, g2d).getWidth();
      int x = (painelJogo.larguraTela/2) - (comprimentoTextoCentralizado/2);
      return x;
   }

   /**
    * Desenha o texto selecionado com uma ilusão de sombra utilizando o relevo já definito anteriormente
    * @param g2d Graficos 2D usados na renderização do jogo 
    * @param texto Texto que será desenhado
    * @param textoX Posição X do texto
    * @param textoY Posição Y do texto
    * @param corTexto Cor do texto
    * @param corSombra Cor da sombra do texto que será usada no relevo
    */
   private void desenharTextoComSombra(Graphics2D g2d, String texto, int textoX, int textoY, Color corTexto, Color corSombra){
      g2d.setColor(corSombra);
      int sombaX = textoX + relevoSombra;
      int sombraY = textoY + relevoSombra;
      g2d.drawString(texto, sombaX, sombraY);
      
      g2d.setColor(corTexto);
      g2d.drawString(texto, textoX, textoY);
   }

   /**
    * Função utilizada para desenhar um ícone antes do texto em destaque por
    * qualquer função das telas de menu
    * @param g2d Graficos 2D usados na renderização do jogo 
    * @param icone Imagem que será desenhada
    * @param posX Posição X da imagem
    * @param posY Posição Y da imagem
    */
   private void desenharIcone(Graphics2D g2d, Image icone, int posX, int posY){
      desenhoX = posX - (painelJogo.tamanhoBloco*2);
      desenhoY = posY - painelJogo.tamanhoBloco;
      g2d.drawImage(icone, desenhoX, desenhoY,null);
   }

   /**
    * Função responsável por atualizar as cores dos textos de acordo 
    * com a configuração escoliha pelo usuário
    */
   public void atualizarTemaCores(){
      if(painelJogo.temaCoresAtual == 1){
         corTexto = corTextoPadrao;
         corTextoSelecionado = corTextoSelecionadoPadrao;
         corSombra = corSombraPadrao;
      
      }else if(painelJogo.temaCoresAtual == 2){
         corTexto = corTexto2;
         corTextoSelecionado = corTextoSelecionado2;
         corSombra = corSombra2;
      
      }else if(painelJogo.temaCoresAtual == 3){
         corTexto = corTexto3;
         corTextoSelecionado = corTextoSelecionado3;
         corSombra = corSombra3;
      }
   }
   
}

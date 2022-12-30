package render;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Image;

import java.util.ArrayList;

import entidade.No;
import estrutura.LeitorTeclado;
import estrutura.Logica;
import estrutura.Som;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class PainelJogo extends JPanel implements Runnable{
   final int tamanhoBlocoOriginal = 5;
   final int maximoColunas = 28;
   final int maximoLinhas = 28;
   final public int scale = 4;

   public final int tamanhoBloco = scale * tamanhoBlocoOriginal;
   public final int larguraTela = maximoColunas * tamanhoBloco;
   public final int alturaTela = maximoLinhas * tamanhoBloco;

   public Image iconeJogo = new ImageIcon(getClass().getResource("/images/icone_jogo.png")).getImage();

   public String direcao = "nenhuma";
   public final int velocidadeMinima = tamanhoBloco;//60fps - vel minima 4
   public final int velocidadeMaxima = tamanhoBloco;//60fps - vel maxima 7
   public int velocidade;
   public int temaCoresAtual = 1;
   int fps = 15;
   int contadorLixo = 0;

   public ArrayList <No> cobrinha;
   public int tamanho;
   public int pontos;
   public No fruta;
   public boolean temaCoresAlterado = false;
   public boolean atualizarTemaAleatorio = false;

   final public int maxTemaCobrinha = 6;
   public int temaCobrinhaAtual = 1;
   public boolean corTemaCobrinhaAlterado = false;
   public boolean corTemaAleatorio = false;

   public String textoFimJogo = " ";

   public final int estadoJogoExecutando = 1;
   public final int estadoFimJogo = 2;
   public final int estadoTelaInicio = 3;
   public final int estadoTelaConfiguracao = 4;
   public int estadoAtualJogo;
   public int numeroComando = 1;
   public boolean modoIgnorarBorda = false;

   public InterfaceDesenho interfaceDesenho = new InterfaceDesenho(this);
   public Som som = new Som();
   LeitorTeclado leitorTeclado = new LeitorTeclado(this);
   Logica logica = new Logica();   
   Thread gameThread;

   //animacao da tela inicial
   public ArrayList <No> cobrinha_menu = new ArrayList<>();
   public BufferedImage rosto_cima, rosto_baixo, rosto_direita, corpo_cobrinha;
   public int contador_menu = 1;
   final int animacao_subindo = 1;
   final int animacao_descendo = 2;
   int temaCobrinhaMenu = 1;
   int animacao_vez;
   int valor_subir_rosto = 7;
   int valor_descer_rosto = 24;
   
   public PainelJogo(){
      setPreferredSize(new Dimension(larguraTela, alturaTela));
      setBackground(Color.BLACK);
      addKeyListener(leitorTeclado);
      setFocusable(true);
      setVisible(true);
      setDoubleBuffered(true);//parece melhorar o desempenho

      gerarCobrinhaMenu();
   }

   /**
    * Inicia a thread do jogo usando o Painel 
    */
   public void startGameThread(){
      gameThread = new Thread(this);
      // tentar evitar o glitch que pisca a tela de tempos em tempos
      gameThread.setPriority(Thread.MAX_PRIORITY);
      gameThread.start();
   }

   /**
    Atribui os valores padrão necessários.
    Reinicia as teclas lidas, os pontos, zera o tamanho da cobrinha, define a direção padrão
    gera uma nova fruta e altera o estado do jogo para tela de inicio
    ***/
   public void setup(){
      leitorTeclado.zerarTeclas();

      pontos = 0;

      cobrinha = logica.gerarCobrinha(0, 0, tamanhoBloco);
      
      tamanho = cobrinha.size();
      direcao = "direita";

      logica.gerarFruta(this);

      estadoAtualJogo = estadoTelaInicio;
   }

   /**
    Inicia valores que serão usados no cálculo que define o fps do jogo com base na variáveis "fps".
    atribui a velocidade inicial aqui para que após o início de uma nova partida, caso o jogador tenha
    alterado a velocidade pelo menu de configurações, o valor seja mantido. Usa um método que ao decorrer
    da execução do programa invoca o Grabage Collector para evitar estouro de memória
   */
   @Override   
   public void run(){
      //metodo delta para o calculo do fps
      double intervalo = 1000000000/fps;
      double delta = 0; 
      long ultimoTempo = System.nanoTime();
      long tempoAtual;

      // fora do setup pra caso o jogador continue mesmo depois de perder uma rodada
      //manter a velocidade escolhida
      velocidade = velocidadeMinima;
      animacao_vez = animacao_subindo;
      logica.atualizarIcones(this, interfaceDesenho);
      setup();
      som.tocarMusicaMenu();
      while(gameThread != null){

         tempoAtual = System.nanoTime();
         delta += (tempoAtual - ultimoTempo)/intervalo;
         ultimoTempo = tempoAtual;

         if(delta > 1){
            atualizar();        
            repaint();
            limparLixo();
            delta--;
         }
      }
   }

   /*** 
    Executa o jogo com base no valor do estado atual, ficando na responsabilidade
    da lógica do jogo gerenciar o que fazer
   ***/
   public void atualizar(){

      if(temaCoresAlterado){
         interfaceDesenho.atualizarTemaCores();
         temaCoresAlterado = false;
      }

      if(corTemaCobrinhaAlterado){
         logica.atualizarIcones(this, interfaceDesenho);
         temaCoresAlterado = false;
      }

      if(estadoAtualJogo == estadoTelaInicio){
         logica.rodarTelaComecoJogo(this, leitorTeclado, som);
      
      }else if(estadoAtualJogo == estadoTelaConfiguracao){
         logica.rodarTelaConfiguracao(this, leitorTeclado, som);
      
      }else if(estadoAtualJogo == estadoJogoExecutando){
         logica.rodarJogo(this, leitorTeclado, som, interfaceDesenho);
      
      }else if(estadoAtualJogo == estadoFimJogo){
         if(cobrinha.size() > 1) cobrinha.clear();
         logica.rodarTelaFimJogo(this, leitorTeclado, som);
      }
   }

   /**
    * Método responsável pelo gerenciamento de desenho no painel
    */
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;

      if(estadoAtualJogo == estadoTelaInicio){
         interfaceDesenho.telaComecoJogo(g2);
         animarCobrinhaMenu(g2);
      
      }else if(estadoAtualJogo == estadoTelaConfiguracao){
         interfaceDesenho.telaConfiguracao(g2);
      
      }else if(estadoAtualJogo == estadoJogoExecutando){
         interfaceDesenho.telaJogo(g2, this);
        
      
      }else if(estadoAtualJogo == estadoFimJogo){
         interfaceDesenho.telaFimDeJogo(g2, textoFimJogo);
      }

      g2.dispose();
   }

   /**
    * Gera todos os componente necessarios para a cobrinha do menu que será animada,
    * não é necessário uma imagem correspondete ao rosto apontado para a esquerda
    */
   private void gerarCobrinhaMenu(){
      rosto_cima = interfaceDesenho.rosto_cima_padrao;
      rosto_baixo = interfaceDesenho.rosto_baixo_padrao;
      rosto_direita = interfaceDesenho.rosto_direita_padrao;
      corpo_cobrinha = interfaceDesenho.corpo_cobrinha_padrao;

      int x = 0;
      int y = larguraTela/2;

      for(int i = 0; i < 8; i++){
         No no = new No(x, y);
         if(i == 0){
            no.icone = rosto_direita;
         }else{
            no.icone = corpo_cobrinha;
         }
         cobrinha_menu.add(no);
         x -= tamanhoBloco;
      }
   }

   /**
    * Atualza o padrão de cores da cobrinha na tela inicial, não
    * inclui o tema aleatório
    */
   private void atualizarCorCobrinhaMenu(){

      if(temaCobrinhaMenu+1 > maxTemaCobrinha-1) temaCobrinhaMenu = 1;
      else temaCobrinhaMenu++;

      if(temaCobrinhaMenu == 1){
         rosto_cima = interfaceDesenho.rosto_cima_padrao;
         rosto_baixo = interfaceDesenho.rosto_baixo_padrao;
         rosto_direita = interfaceDesenho.rosto_direita_padrao;
         for(int i = 1; i < cobrinha_menu.size(); i++){
            cobrinha_menu.get(i).icone = interfaceDesenho.corpo_cobrinha_padrao;
         }         
      }else if(temaCobrinhaMenu == 2){
         rosto_cima = interfaceDesenho.rosto_cima_2;
         rosto_baixo = interfaceDesenho.rosto_baixo_2;
         rosto_direita = interfaceDesenho.rosto_direita_2;
         for(int i = 1; i < cobrinha_menu.size(); i++){
            cobrinha_menu.get(i).icone = interfaceDesenho.corpo_cobrinha_2;
         }   
      }else if(temaCobrinhaMenu == 3){
         rosto_cima = interfaceDesenho.rosto_cima_3;
         rosto_baixo = interfaceDesenho.rosto_baixo_3;
         rosto_direita = interfaceDesenho.rosto_direita_3;
         for(int i = 1; i < cobrinha_menu.size(); i++){
            cobrinha_menu.get(i).icone = interfaceDesenho.corpo_cobrinha_3;
         }   
      }else if(temaCobrinhaMenu == 4){
         rosto_cima = interfaceDesenho.rosto_cima_4;
         rosto_baixo = interfaceDesenho.rosto_baixo_4;
         rosto_direita = interfaceDesenho.rosto_direita_4;
         for(int i = 1; i < cobrinha_menu.size(); i++){
            cobrinha_menu.get(i).icone = interfaceDesenho.corpo_cobrinha_4;
         }   
      }else if(temaCobrinhaMenu == 5){
         rosto_cima = interfaceDesenho.rosto_cima_5;
         rosto_baixo = interfaceDesenho.rosto_baixo_5;
         rosto_direita = interfaceDesenho.rosto_direita_5;
         for(int i = 1; i < cobrinha_menu.size(); i++){
            cobrinha_menu.get(i).icone = interfaceDesenho.corpo_cobrinha_5;
         }   
      }    
   }

   /**
    * Alterna dentre as diferentes animações de movimento da cobrinha 
    * na tela inical do jogo
    * @param g2d Graficos 2D usados na renderização do jogo 
    */
   private void animarCobrinhaMenu(Graphics2D g2){     
      if(animacao_vez == animacao_subindo) animacaoCima(g2);
      else if(animacao_vez == animacao_descendo) animacaoBaixo(g2);
   }

   /**
    * Método personalizado para animação de uma outra cobrinha na tela inicial
    * @param g2d Graficos 2D usados na renderização do jogo 
    */
   private void animacaoCima(Graphics2D g2){

      if(contador_menu == valor_subir_rosto || contador_menu == valor_subir_rosto+1 || contador_menu == valor_subir_rosto+3){
         cobrinha_menu.get(0).icone = rosto_cima;
         cobrinha_menu.get(0).y -= tamanhoBloco;
      
      }else if(contador_menu == valor_descer_rosto || contador_menu == valor_descer_rosto+1 || contador_menu == valor_descer_rosto+2 ||
               contador_menu == valor_descer_rosto+5 || contador_menu == valor_descer_rosto+9){
         cobrinha_menu.get(0).icone = rosto_baixo;
         cobrinha_menu.get(0).y += tamanhoBloco;
      
      }else{
         cobrinha_menu.get(0).icone = rosto_direita;
         cobrinha_menu.get(0).x += tamanhoBloco;
      }

      interfaceDesenho.desenharCobrinha(g2, cobrinha_menu, "direita", true);

      logica.perseguirCauda(cobrinha_menu);

      contador_menu++;
      if(contador_menu == 50){
         cobrinha_menu.get(0).x = 0;
         cobrinha_menu.get(0).y = alturaTela/2;
         contador_menu = 0;
         animacao_vez = animacao_descendo;
         atualizarCorCobrinhaMenu();
      }
   }

   /**
    * Método personalizado para animação de uma outra cobrinha na tela inicial
    * @param g2d Graficos 2D usados na renderização do jogo 
    */
   private void animacaoBaixo(Graphics2D g2){
      if(contador_menu == valor_descer_rosto-11 || contador_menu == valor_descer_rosto-12 || contador_menu == valor_descer_rosto-16 ||
         contador_menu == valor_descer_rosto-17 || contador_menu == valor_descer_rosto-18 || contador_menu == valor_descer_rosto-20){
         cobrinha_menu.get(0).icone = rosto_baixo;
         cobrinha_menu.get(0).y += tamanhoBloco;
      
      }else if(contador_menu == valor_subir_rosto+18 || contador_menu == valor_subir_rosto+19 || contador_menu == valor_subir_rosto+24 ||
               contador_menu == valor_subir_rosto+27 || contador_menu == valor_subir_rosto+28){
         cobrinha_menu.get(0).icone = rosto_cima;
         cobrinha_menu.get(0).y -= tamanhoBloco;

      }else{
         cobrinha_menu.get(0).icone = rosto_direita;
         cobrinha_menu.get(0).x += tamanhoBloco;
      }

      interfaceDesenho.desenharCobrinha(g2, cobrinha_menu, "direita", true);

      logica.perseguirCauda(cobrinha_menu);

      contador_menu++;
      if(contador_menu == 50){
         cobrinha_menu.get(0).x = 0;
         cobrinha_menu.get(0).y = alturaTela/2;
         contador_menu = 0;
         animacao_vez = animacao_subindo;
         atualizarCorCobrinhaMenu();
      }
   }

   /**
    Função criada para executar a música do menu separadamente dos 
    efeitos sonoros do jogo
   ***/
   public void tocarLoopMusica(int indice){
      som.tocarMusica(indice);
      som.tocarLoop();
   }

   /**
    * Limpar lixo de memória, tempo calculado com base no fps do jogo
    */
   public void limparLixo(){
      contadorLixo++;
      if(contadorLixo == (fps * 30)){
         System.gc();
         contadorLixo = 0;
      }
   }

}

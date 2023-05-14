package estrutura;

import render.InterfaceDesenho;
import render.PainelJogo;

import java.util.Random;
import java.util.ArrayList;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import entidade.No;

/**
 * Classe responsavel por toda a lógica por trás do jogo
 */
public class Logica{

   int contador;
   final int numeroOpcoesTelaPrincipal = 3;
   final int numeroOpcoesTelaConfiguracoes = 3;
   final int numeroOpcoesTelaFimDeJogo = 3;
   int x, y;

   Random random = new Random();
   int valorRandom = 1;

   public Logica(){}

   /**
    * 
    * @param x Valor inicial da cabeça da cobrinha em relação a X
    * @param y Valor inicial da cabeça da cobrinha em relação a Y
    * @param tamanhoBloco Valor de cada bloco do jogo já calculado em relação ao scaling da tela
    * @return Retorna uma cobrinha de tamanho 1 contendo apenas a cabeça 
    */
   public ArrayList<No> gerarCobrinha(int x, int y, int tamanhoBloco){
      ArrayList<No> cobrinha = new ArrayList<>();
 
      No no = new No(x, y);
      no.configurarHitbox(tamanhoBloco);
      cobrinha.add(no);
      
      return cobrinha;
   }

   /**
    * Gera uma fruta aleatoriamente dentro das dimensões do painel de jogo.
    * O cálculo é feito com base no tamanho de cada bloco do jogo para que a fruta
    * apareça apenas dentro de cada bloco
    * @param painelJogo Painel em que está sendo renderizado o jogo
    */
   public void gerarFruta(PainelJogo painelJogo){
      boolean colidindo;
      No no;

      do{
         colidindo = false;

         x = new Random().nextInt(painelJogo.larguraTela - painelJogo.tamanhoBloco);
         y = new Random().nextInt(painelJogo.alturaTela - painelJogo.tamanhoBloco);
   
         //gerar em valores dentro da grade do jogo
         x /= painelJogo.tamanhoBloco;
         x *= painelJogo.tamanhoBloco;
         y /= painelJogo.tamanhoBloco;
         y *= painelJogo.tamanhoBloco;
         no = new No(x, y);
         no.configurarHitbox(painelJogo.tamanhoBloco);
         
         for(contador = 0; contador < painelJogo.cobrinha.size()-1; contador++){
            painelJogo.cobrinha.get(contador).configurarHitbox(painelJogo.tamanhoBloco);
            if(painelJogo.cobrinha.get(contador).hitbox.intersects(no.hitbox)){
               colidindo = true;
               break;
            }
         }

      }while(colidindo);

      painelJogo.fruta = no;
      painelJogo.fruta.configurarHitbox(painelJogo.tamanhoBloco);
   }

   /**
    * Adiciona uma nova parte ao corpo da cobrinha, a imagem será referente ao
    * esquema de cores atual do jogo, sendo no esquema aleatório será sorteado um valor de uma imagem
    * já existente dentro dos arquivos do jogo
    * @param painelJogo Painel em que está sendo renderizado o jogo
    * @param interfaceDesenho Objeto responsável pelos desenhos no painel
    */
   private void adicionarNo(PainelJogo painelJogo, InterfaceDesenho interfaceDesenho){
      No no = new No(painelJogo.cobrinha.get(painelJogo.tamanho-1).x, painelJogo.cobrinha.get(painelJogo.tamanho-1).y);
      
      if(painelJogo.corTemaAleatorio){
         valorRandom = random.nextInt(painelJogo.maxTemaCobrinha-1) + 1;
         switch(valorRandom){
            case 1:
               no.icone = interfaceDesenho.corpo_cobrinha_padrao;
            break;        
   
            case 2:
               no.icone = interfaceDesenho.corpo_cobrinha_2;
            break;
   
            case 3:
               no.icone = interfaceDesenho.corpo_cobrinha_3;
            break;
   
            case 4:
               no.icone = interfaceDesenho.corpo_cobrinha_4;
            break;
   
            case 5:
               no.icone = interfaceDesenho.corpo_cobrinha_5;
            break;
         }
      }else{
         no.icone = interfaceDesenho.corpo_atual;
      }
      no.configurarHitbox(painelJogo.tamanhoBloco);
      painelJogo.cobrinha.add(no);
      painelJogo.tamanho++;
   }

   /**
    * Referente ao tema de cor aleatório da cobrinha, será sorteado um valor de uma imagem
    * já existente dentro dos arquivos do jogo
    * @param painelJogo Painel em que está sendo renderizado o jogo
    * @param interfaceDesenho Objeto responsável pelos desenhos no painel
    */
   public void sortearImagensCabeca(PainelJogo painelJogo, InterfaceDesenho interfaceDesenho){
      valorRandom = random.nextInt(painelJogo.maxTemaCobrinha-1) + 1;
      switch(valorRandom){//ARRUMAR direita
         case 1:
            interfaceDesenho.rosto_cima_atual = interfaceDesenho.rosto_cima_padrao;
            interfaceDesenho.rosto_baixo_atual = interfaceDesenho.rosto_baixo_padrao;
            interfaceDesenho.rosto_esquerda_atual = interfaceDesenho.rosto_esquerda_padrao;
            interfaceDesenho.rosto_direita_atual = interfaceDesenho.rosto_direita_padrao;
            
            interfaceDesenho.imagemCobrinha = interfaceDesenho.rosto_baixo_padrao;
            interfaceDesenho.corpoExemplo = interfaceDesenho.corpo_cobrinha_padrao;
         break;        

         case 2:
            interfaceDesenho.rosto_cima_atual = interfaceDesenho.rosto_cima_2;
            interfaceDesenho.rosto_baixo_atual = interfaceDesenho.rosto_baixo_2;
            interfaceDesenho.rosto_esquerda_atual = interfaceDesenho.rosto_esquerda_2;
            interfaceDesenho.rosto_direita_atual = interfaceDesenho.rosto_direita_2;

            interfaceDesenho.imagemCobrinha = interfaceDesenho.rosto_baixo_2;
            interfaceDesenho.corpoExemplo = interfaceDesenho.corpo_cobrinha_2;
         break;

         case 3:
            interfaceDesenho.rosto_cima_atual = interfaceDesenho.rosto_cima_3;
            interfaceDesenho.rosto_baixo_atual = interfaceDesenho.rosto_baixo_3;
            interfaceDesenho.rosto_esquerda_atual = interfaceDesenho.rosto_esquerda_3;
            interfaceDesenho.rosto_direita_atual = interfaceDesenho.rosto_direita_3;
            
            interfaceDesenho.imagemCobrinha = interfaceDesenho.rosto_baixo_3;
            interfaceDesenho.corpoExemplo = interfaceDesenho.corpo_cobrinha_3;
         break;

         case 4:
            interfaceDesenho.rosto_cima_atual = interfaceDesenho.rosto_cima_4;
            interfaceDesenho.rosto_baixo_atual = interfaceDesenho.rosto_baixo_4;
            interfaceDesenho.rosto_esquerda_atual = interfaceDesenho.rosto_esquerda_4;
            interfaceDesenho.rosto_direita_atual = interfaceDesenho.rosto_direita_4;

            interfaceDesenho.imagemCobrinha = interfaceDesenho.rosto_baixo_4;
            interfaceDesenho.corpoExemplo = interfaceDesenho.corpo_cobrinha_4;   
         break;

         case 5:
            interfaceDesenho.rosto_cima_atual = interfaceDesenho.rosto_cima_5;
            interfaceDesenho.rosto_baixo_atual = interfaceDesenho.rosto_baixo_5;
            interfaceDesenho.rosto_esquerda_atual = interfaceDesenho.rosto_esquerda_5;
            interfaceDesenho.rosto_direita_atual = interfaceDesenho.rosto_direita_5;

            interfaceDesenho.imagemCobrinha = interfaceDesenho.rosto_baixo_5;
            interfaceDesenho.corpoExemplo = interfaceDesenho.corpo_cobrinha_5;
         break;
      }
      painelJogo.atualizarTemaAleatorio = false;
   }
 
   /**
    * Referente ao tema de cor aleatório da cobrinha, será sorteado um valor de uma imagem
    * já existente dentro dos arquivos do jogo
    * @param painelJogo Painel em que está sendo renderizado o jogo
    * @param interfaceDesenho Objeto responsável pelos desenhos no painel
    * @return Retorna uma imagem de corpo da cobrinha
    */
   public BufferedImage gerarCorpoAleatorio(PainelJogo painelJogo, InterfaceDesenho interfaceDesenho){
      BufferedImage imagem = null;
      valorRandom = random.nextInt(painelJogo.maxTemaCobrinha-1) + 1;
      switch(valorRandom){
         case 1:
            imagem = interfaceDesenho.corpo_cobrinha_padrao;
         break;        

         case 2:
         imagem = interfaceDesenho.corpo_cobrinha_2;
         break;

         case 3:
         imagem = interfaceDesenho.corpo_cobrinha_3;
         break;

         case 4:
         imagem = interfaceDesenho.corpo_cobrinha_4;
         break;

         case 5:
         imagem = interfaceDesenho.corpo_cobrinha_5;
         break;
      }
      return imagem;
   }

   /**
    * Faz o escalamento da imagem antes da execução do jogo para poupar recursos de hardware e
    * evitar escalar no tempo de execução
    * @param imagemOriginal Imagem que será escalada
    * @param comprimento Comprimento final referente ao escalamento da janela
    * @param altura Altura final referente ao escalamento da janela
    * @return Retorna a imagem com tamanho proporcional ao scaling da tela
    */
   public BufferedImage imagemScalada(BufferedImage imagemOriginal, int comprimento, int altura){
      BufferedImage imagemScalada = new BufferedImage(comprimento, altura, imagemOriginal.getType());
      Graphics2D g2d = imagemScalada.createGraphics();
      g2d.drawImage(imagemOriginal, 0, 0, comprimento, altura, null);
      g2d.dispose();
      return imagemScalada;
   }

   /**
    * Se refere a tela de execução do jogo, então conta com os métodos necessários para isso,
    * como o de perserguir a cauda da cobrinha, atualizar cada hitbox de cada parte do corpo da cobrinha após cada
    * movimento, determinar a direção com base nos eventos lidos pelo LeitorTeclado, movimentar a cobrinha de acordo
    * com a direção atual, detectar as colisões de corpo e com a fruta, além de, de acordo com a configuração escolhida, tratar
    * a colisão com a borda da tela
    * @param painelJogo Painel em que está sendo renderizado o jogo
    * @param leitorTeclado Objeto responsável por ler os eventos do teclado em relação ao jogo
    * @param som Objeto responsável pelos eventos de som do jogo
    * @param interfaceDesenho Objeto responsável pelos desenhos no painel
    */
   public void rodarJogo(PainelJogo painelJogo, LeitorTeclado leitorTeclado, Som som, InterfaceDesenho interfaceDesenho){
      perseguirCauda(painelJogo.cobrinha);

      atualizarHitbox(painelJogo);

      if(leitorTeclado.wPressionado && !painelJogo.direcao.equalsIgnoreCase("baixo")) painelJogo.direcao = "cima";
      else if(leitorTeclado.aPressionado && !painelJogo.direcao.equalsIgnoreCase("direita")) painelJogo.direcao = "esquerda";
      else if(leitorTeclado.sPressionado && !painelJogo.direcao.equalsIgnoreCase("cima")) painelJogo.direcao = "baixo";
      else if(leitorTeclado.dPressionado && !painelJogo.direcao.equalsIgnoreCase("esquerda")) painelJogo.direcao = "direita";
      
      moverCobrinha(painelJogo);

      if(detectarSaidaMapa(painelJogo) && painelJogo.modoIgnorarBorda){        
         reposicionarCobrinha(painelJogo);
      }

      if(detectarColisaoInterna(painelJogo)){
         som.tocarMusica(3);
         painelJogo.textoFimJogo = "Se enrolou";
         painelJogo.estadoAtualJogo = painelJogo.estadoFimJogo;
      }
      
      if(detectarSaidaMapa(painelJogo) && !painelJogo.modoIgnorarBorda){
         som.tocarMusica(3);
         painelJogo.textoFimJogo = "Fim de jogo";
         painelJogo.estadoAtualJogo = painelJogo.estadoFimJogo;   
      }

      //tem que ficar pro final se nao buga e acusa colisao quando pega a primeira fruta
      if(detectarColisaoFruta(painelJogo)){
         som.tocarMusica(4);
         gerarFruta(painelJogo);
         adicionarNo(painelJogo, interfaceDesenho);
         painelJogo.pontos++;
      }
   }

   /**
    * Reorganiza os dados de hitbox de cada parte do corpo da cobrinha
    * @param painelJogo Painel em que está sendo renderizado o jogo
    */
   public void atualizarHitbox(PainelJogo painelJogo){
      for(contador = 0; contador < painelJogo.cobrinha.size(); contador++){
         painelJogo.cobrinha.get(0).configurarHitbox(painelJogo.tamanhoBloco);
      }
   }

   /**
    * Função responsável por gerar o movimento da cobrinha de acordo
    * com a sua direção atual 
    * @param painelJogo Painel em que está sendo renderizado o jogo
    */
   public void moverCobrinha(PainelJogo painelJogo){
      switch(painelJogo.direcao){
         case "cima":
            painelJogo.cobrinha.get(0).y -= painelJogo.velocidade;                 
         break;
  
         case "esquerda":
            painelJogo.cobrinha.get(0).x -= painelJogo.velocidade;
         break;
  
         case "baixo":
            painelJogo.cobrinha.get(0).y += painelJogo.velocidade;
         break;
  
          case "direita":
            painelJogo.cobrinha.get(0).x += painelJogo.velocidade;
         break;
      }
   }
 
   /**
    * Calcula se houve uma colisão entre a cabeça da cobrinha e 
    * o corpo dela 
    * @param painelJogo Painel em que está sendo renderizado o jogo
    * @return Retorna um valor booleano referente à verificação
    */
   private boolean detectarColisaoInterna(PainelJogo painelJogo){
      for(contador = 0; contador < painelJogo.tamanho; contador++){
         //nao checar colisao com o rosto
         if(contador == 0) continue;

         if(painelJogo.cobrinha.get(0).x == painelJogo.cobrinha.get(contador).x &&
         painelJogo.cobrinha.get(0).y == painelJogo.cobrinha.get(contador).y){
            return true;
         }
      }
      return false;
   }

   /**
    * Verifica de acordo com as dimensões do painel do jogo
    * se a cobrinha colodiu com as bordas
    * @param painelJogo Painel em que está sendo renderizado o jogo
    * @return Retorna um valor booleano referente à verificação
    */
   private boolean detectarSaidaMapa(PainelJogo painelJogo){
      if(painelJogo.cobrinha.get(0).x > painelJogo.larguraTela - painelJogo.tamanhoBloco) return true;
      if(painelJogo.cobrinha.get(0).x < 0) return true;
      if(painelJogo.cobrinha.get(0).y > painelJogo.alturaTela - painelJogo.tamanhoBloco) return true;
      if(painelJogo.cobrinha.get(0).y < 0) return true;
      return false;
   }

   /**
    * Vefirica se a hitbox da cabeça da cobrinha colidiu com a hitbox da fruta 
    * @param painelJogo Painel em que está sendo renderizado o jogo
    * @return Retorna um valor booleano referente à verificação
    */
   public boolean detectarColisaoFruta(PainelJogo painelJogo){
      if(painelJogo.cobrinha.get(0).hitbox.intersects(painelJogo.fruta.hitbox)) return true;
      return false;
   }

   /**
    * Recalcula as posições das partes do corpo da cobrinha em
    * relação a cabeça para serem reposicionadas 
    * @param painelJogo Painel em que está sendo renderizado o jogo
    */
   public void perseguirCauda(ArrayList <No> cobrinha) {
      for(contador = cobrinha.size() - 1; contador > 0; contador--){
         cobrinha.get(contador).x = cobrinha.get(contador-1).x;
         cobrinha.get(contador).y = cobrinha.get(contador-1).y;
      }
   }

   /**
    * Função referente à configuração de "Ignorar bordas", quando a
    * cobrinha colidir com as estremidades da tela, ela será reposicionada
    * na extremidade oposta
    * @param painelJogo Painel em que está sendo renderizado o jogo
    */
   private void reposicionarCobrinha(PainelJogo painelJogo){
      if(painelJogo.cobrinha.get(0).x > painelJogo.larguraTela - painelJogo.tamanhoBloco) painelJogo.cobrinha.get(0).x = 0;
      else if(painelJogo.cobrinha.get(0).x < 0) painelJogo.cobrinha.get(0).x = painelJogo.larguraTela - painelJogo.tamanhoBloco;
      else if(painelJogo.cobrinha.get(0).y > painelJogo.alturaTela - painelJogo.tamanhoBloco) painelJogo.cobrinha.get(0).y = 0;
      else if(painelJogo.cobrinha.get(0).y < 0) painelJogo.cobrinha.get(0).y = painelJogo.alturaTela - painelJogo.tamanhoBloco;      
   }

   /**
    * De acordo com a mudança do esquema de icones da cobrinha pelo menu de configurações
    * este método é invocado atualizando as partes de corpos atuais da cobrinha pelas suas 
    * correspondentes ao tema atual escolhido
    * @param painelJogo Painel em que está sendo renderizado o jogo
    * @param interfaceDesenho Objeto responsável pelos desenhos no painel
    */
   public void atualizarIcones(PainelJogo painelJogo, InterfaceDesenho interfaceDesenho){
      if(painelJogo.temaCobrinhaAtual != 6){
         painelJogo.corTemaAleatorio = false;
      }
      if(painelJogo.temaCobrinhaAtual == 1){
         interfaceDesenho.imagemCobrinha = interfaceDesenho.rosto_baixo_padrao;

         interfaceDesenho.rosto_cima_atual = interfaceDesenho.rosto_cima_padrao;
         interfaceDesenho.rosto_baixo_atual = interfaceDesenho.rosto_baixo_padrao;
         interfaceDesenho.rosto_esquerda_atual = interfaceDesenho.rosto_esquerda_padrao;
         interfaceDesenho.rosto_direita_atual = interfaceDesenho.rosto_direita_padrao;
         interfaceDesenho.corpo_atual = interfaceDesenho.corpo_cobrinha_padrao;
      
      }else if(painelJogo.temaCobrinhaAtual == 2){
         interfaceDesenho.imagemCobrinha = interfaceDesenho.rosto_baixo_2;

         interfaceDesenho.rosto_cima_atual = interfaceDesenho.rosto_cima_2;
         interfaceDesenho.rosto_baixo_atual = interfaceDesenho.rosto_baixo_2;
         interfaceDesenho.rosto_esquerda_atual = interfaceDesenho.rosto_esquerda_2;
         interfaceDesenho.rosto_direita_atual = interfaceDesenho.rosto_direita_2;
         interfaceDesenho.corpo_atual = interfaceDesenho.corpo_cobrinha_2;
      
      }else if(painelJogo.temaCobrinhaAtual == 3){
         interfaceDesenho.imagemCobrinha = interfaceDesenho.rosto_baixo_3;

         interfaceDesenho.rosto_cima_atual = interfaceDesenho.rosto_cima_3;
         interfaceDesenho.rosto_baixo_atual = interfaceDesenho.rosto_baixo_3;
         interfaceDesenho.rosto_esquerda_atual = interfaceDesenho.rosto_esquerda_3;
         interfaceDesenho.rosto_direita_atual = interfaceDesenho.rosto_direita_3;
         interfaceDesenho.corpo_atual = interfaceDesenho.corpo_cobrinha_3;
      
      }else if(painelJogo.temaCobrinhaAtual == 4){
         interfaceDesenho.imagemCobrinha = interfaceDesenho.rosto_baixo_4;

         interfaceDesenho.rosto_cima_atual = interfaceDesenho.rosto_cima_4;
         interfaceDesenho.rosto_baixo_atual = interfaceDesenho.rosto_baixo_4;
         interfaceDesenho.rosto_esquerda_atual = interfaceDesenho.rosto_esquerda_4;
         interfaceDesenho.rosto_direita_atual = interfaceDesenho.rosto_direita_4;
         interfaceDesenho.corpo_atual = interfaceDesenho.corpo_cobrinha_4;

      }else if(painelJogo.temaCobrinhaAtual == 5){
         interfaceDesenho.imagemCobrinha = interfaceDesenho.rosto_baixo_5;

         interfaceDesenho.rosto_cima_atual = interfaceDesenho.rosto_cima_5;
         interfaceDesenho.rosto_baixo_atual = interfaceDesenho.rosto_baixo_5;
         interfaceDesenho.rosto_esquerda_atual = interfaceDesenho.rosto_esquerda_5;
         interfaceDesenho.rosto_direita_atual = interfaceDesenho.rosto_direita_5;
         interfaceDesenho.corpo_atual = interfaceDesenho.corpo_cobrinha_5;
      }else if(painelJogo.temaCobrinhaAtual == 6){
         painelJogo.corTemaAleatorio = true;//atualiza o texto nas configs
      }
   }

   //------ logica das telas ----------------

   /**
    * Método responsavel por executar toda a lógica por trás da tela inicial do jogo
    * @param painelJogo Painel em que está sendo renderizado o jogo
    * @param leitorTeclado Objeto responsável por ler os eventos do teclado em relação ao jogo
    * @param som Objeto responsável pelos eventos de som do jogo
    */
   public void rodarTelaComecoJogo(PainelJogo painelJogo, LeitorTeclado leitorTeclado, Som som){

      if(leitorTeclado.wPressionado){
         som.tocarMusica(0);
         if(painelJogo.numeroComando-1 < 1){
            painelJogo.numeroComando = numeroOpcoesTelaPrincipal;
         }else{
            painelJogo.numeroComando --;
         }        
         leitorTeclado.wPressionado = false;
      
      }else if(leitorTeclado.sPressionado){
         som.tocarMusica(0);
         if(painelJogo.numeroComando+1 > numeroOpcoesTelaPrincipal){
            painelJogo.numeroComando = 1;
         }else{
            painelJogo.numeroComando ++;
         }         
         leitorTeclado.sPressionado = false;
      
      }else if(leitorTeclado.enterPressionado){
         if(painelJogo.numeroComando == 1){//iniciar novo jogo
            painelJogo.som.pararMusicaMenu();                     
            painelJogo.setup();
            painelJogo.estadoAtualJogo = painelJogo.estadoJogoExecutando;
            painelJogo.som.tocarMusica(2);               
         }else if(painelJogo.numeroComando == 2){//tela de configuraçoes
            painelJogo.som.tocarMusica(2);
            painelJogo.estadoAtualJogo = painelJogo.estadoTelaConfiguracao;
            painelJogo.numeroComando = 1;
         }else if(painelJogo.numeroComando == 3){//sair
            painelJogo.som.tocarMusica(2);
            System.exit(0);
         }
      }
   }

   /**
    * Método responsavel por executar toda a lógica por trás da tela de configurações do jogo
    * @param painelJogo Painel em que está sendo renderizado o jogo
    * @param leitorTeclado Objeto responsável por ler os eventos do teclado em relação ao jogo
    * @param som Objeto responsável pelos eventos de som do jogo
    */
   public void rodarTelaConfiguracao(PainelJogo painelJogo, LeitorTeclado leitorTeclado, Som som){
      if(leitorTeclado.escPressionado){
         som.tocarMusica(1);
         painelJogo.estadoAtualJogo = painelJogo.estadoTelaInicio;         
         leitorTeclado.escPressionado = false;
      
      }else if(leitorTeclado.wPressionado){
         som.tocarMusica(0);
         if(painelJogo.numeroComando-1 < 1){
            painelJogo.numeroComando = numeroOpcoesTelaConfiguracoes;
         }else{
            painelJogo.numeroComando --;
         }  

      }else if(leitorTeclado.sPressionado){
         som.tocarMusica(0);
         if(painelJogo.numeroComando+1 > numeroOpcoesTelaConfiguracoes){
            painelJogo.numeroComando = 1;
         }else{
            painelJogo.numeroComando ++;
         }  

      }else if((painelJogo.numeroComando == 1) && (leitorTeclado.aPressionado || leitorTeclado.dPressionado)){
         som.tocarMusica(0);
         if(painelJogo.modoIgnorarBorda){
            painelJogo.modoIgnorarBorda = false;
         }else{
            painelJogo.modoIgnorarBorda = true;
         }

      }else if(painelJogo.numeroComando == 2){// mudar cor tema
         if(leitorTeclado.aPressionado){
            som.tocarMusica(0);
            if(painelJogo.temaCoresAtual-1 < 1){
               painelJogo.temaCoresAtual = 3;
            }else{
               painelJogo.temaCoresAtual--;
            }
            painelJogo.temaCoresAlterado = true;                 
         
         }else if(leitorTeclado.dPressionado){
            som.tocarMusica(0);
            if(painelJogo.temaCoresAtual+1 > 3){
               painelJogo.temaCoresAtual = 1;
            }else{
               painelJogo.temaCoresAtual++;
            }
            painelJogo.temaCoresAlterado = true;            
         }
      
      }else if(painelJogo.numeroComando == 3){// mudar tema cobrinha
         if(leitorTeclado.aPressionado){
            som.tocarMusica(0);
            if(painelJogo.temaCobrinhaAtual-1 < 1){
               painelJogo.temaCobrinhaAtual = painelJogo.maxTemaCobrinha;
            }else{
               painelJogo.temaCobrinhaAtual --;
            }
            if(painelJogo.temaCobrinhaAtual == 6){//atualizar nas configs
               sortearImagensCabeca(painelJogo, painelJogo.interfaceDesenho);
               painelJogo.interfaceDesenho.corpoExemplo = gerarCorpoAleatorio(painelJogo, painelJogo.interfaceDesenho);
            }
            painelJogo.corTemaCobrinhaAlterado = true;
         
         }else if(leitorTeclado.dPressionado){
            som.tocarMusica(0);
            if(painelJogo.temaCobrinhaAtual+1 > painelJogo.maxTemaCobrinha){
               painelJogo.temaCobrinhaAtual = 1;
            }else{
               painelJogo.temaCobrinhaAtual++;
            }
            if(painelJogo.temaCobrinhaAtual == 6){//atualizar nas configs
               sortearImagensCabeca(painelJogo, painelJogo.interfaceDesenho);
               painelJogo.interfaceDesenho.corpoExemplo = gerarCorpoAleatorio(painelJogo, painelJogo.interfaceDesenho);
            }
            painelJogo.corTemaCobrinhaAlterado = true;
            
         }
      }      
      leitorTeclado.zerarTeclas();
   }

   /**
    * Método responsavel por executar toda a lógica por trás da tela de fim de jogo
    * @param painelJogo Painel em que está sendo renderizado o jogo
    * @param leitorTeclado Objeto responsável por ler os eventos do teclado em relação ao jogo
    * @param som Objeto responsável pelos eventos de som do jogo
    */
   public void rodarTelaFimJogo(PainelJogo painelJogo, LeitorTeclado leitorTeclado, Som som){
      if(leitorTeclado.wPressionado){
         som.tocarMusica(0);
         if(painelJogo.numeroComando-1 < 1){
            painelJogo.numeroComando = numeroOpcoesTelaFimDeJogo;
         }else{
            painelJogo.numeroComando --;
         }
         leitorTeclado.wPressionado = false;
      
      }else if(leitorTeclado.sPressionado){
         som.tocarMusica(0);
         if(painelJogo.numeroComando+1 > numeroOpcoesTelaFimDeJogo){
            painelJogo.numeroComando = 1;
         }else{
            painelJogo.numeroComando ++;
         }
         leitorTeclado.sPressionado = false;
      
      }else if(leitorTeclado.enterPressionado){
         som.tocarMusica(2);
         if(painelJogo.numeroComando == 1){
            painelJogo.setup();
            painelJogo.estadoAtualJogo = painelJogo.estadoJogoExecutando;
            leitorTeclado.zerarTeclas();
         }
         if(painelJogo.numeroComando == 2){
            painelJogo.numeroComando = 1;
            painelJogo.estadoAtualJogo = painelJogo.estadoTelaInicio;
            painelJogo.som.tocarMusicaMenu();
            leitorTeclado.zerarTeclas();
         }
         if(painelJogo.numeroComando == 3){
            System.exit(0);         
         }
      }
   }

}

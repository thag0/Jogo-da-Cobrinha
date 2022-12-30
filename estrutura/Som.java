package estrutura;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * Classe responsável pelos eventos de som relativos ao jogo
 */
public class Som{
   Clip clipe;

   //instancia separada para ter mais controle e evitar bugs
   Clip musicaMenu;
   URL somURL[] = new URL[10]; 

   /**
    * Captura todos os sons salvos nos arquivos do jogo
    */
   public Som(){
      somURL[0] = getClass().getResource("/som/som_alterar_menu.wav");
      somURL[1] = getClass().getResource("/som/som_voltar_menu.wav");
      somURL[2] = getClass().getResource("/som/som_escolher_menu.wav");
      somURL[3] = getClass().getResource("/som/som_fim_de_jogo.wav");
      somURL[4] = getClass().getResource("/som/som_pegar_fruta.wav");

      somURL[5] = getClass().getResource("/som/musica_menu.wav");
   }

   /**
    * Lê o arquivo correspondente ao índice e salva no objeto clipe
    * @param indice Indice da música de acordo com os arquivos recuperados da pasta do jogo
    */
   private void setFile(int indice){
      try{         
         AudioInputStream ais = AudioSystem.getAudioInputStream(somURL[indice]);
         clipe = AudioSystem.getClip();
         clipe.open(ais);

      }catch(Exception e){
      }
   }

   /**
    * Toca uma única vez o som equivalente ao índice indicado
    * @param indice Indice da música de acordo com os arquivos recuperados da pasta do jogo
    */
   private void tocar(int indice){
      clipe.start();
   }

   /**
    * Configura o arquivo e toca uma única vez o som equivalente ao índice indicado
    * @param indice Indice da música de acordo com os arquivos recuperados da pasta do jogo
    * @param indice
    */
   public void tocarMusica(int indice){
      setFile(indice);
      tocar(indice);
   }

   /**
    * O clipe atual será executado em loop infinito até que o método
    * "pararDeTocar" sejá chamado
    */
   public void tocarLoop(){
      clipe.loop(Clip.LOOP_CONTINUOUSLY);
   }

   /**
    * Para a execução do som que está sendo tocado atualmente
    */
   public void pararDeTocar(){
      clipe.stop();
   }
   
   /**
    * Com auxílio de um objeto de Clip diferente, toca já em loop a música de menu
    * relativa aos arquivos de som do jogo
    */
   public void tocarMusicaMenu(){
      try{        
         AudioInputStream ais = AudioSystem.getAudioInputStream(somURL[5]);
         musicaMenu = AudioSystem.getClip();
         musicaMenu.open(ais);
         musicaMenu.start();
         musicaMenu.loop(Clip.LOOP_CONTINUOUSLY);
      }catch(Exception e){
      }
   }

   /**
    * Para de tocar a música do objeto que está sendo destinado para tocar a música de menu
    */
   public void pararMusicaMenu(){
      musicaMenu.stop();
   }
}

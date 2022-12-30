package estrutura;

import java.awt.event.KeyListener;

import render.PainelJogo;

import java.awt.event.KeyEvent;

/**
 * Classe responsável por escutar os eventos do teclado
 */
public class LeitorTeclado implements KeyListener{

   public boolean wPressionado = false, aPressionado = false, sPressionado = false, dPressionado = false, enterPressionado = false;
   public boolean setaCima = false, setaBaixo = false;
   public boolean escPressionado = false;

   PainelJogo painelJogo;

   public LeitorTeclado(PainelJogo painelJogo){
      this.painelJogo = painelJogo;
   }

   @Override
   public void keyTyped(KeyEvent e){  
   }

   @Override
   public void keyPressed(KeyEvent e){
      int codigoTecla = e.getKeyCode();

      if(painelJogo.estadoAtualJogo == painelJogo.estadoJogoExecutando){
         switch(codigoTecla){
            case KeyEvent.VK_W:
               wPressionado = true;
            break;
   
            case KeyEvent.VK_A:
               aPressionado = true;
            break;
   
            case KeyEvent.VK_S:
               sPressionado = true;
            break;
   
            case KeyEvent.VK_D:
               dPressionado = true;
            break;
         }
      }

      if(painelJogo.estadoAtualJogo == painelJogo.estadoTelaConfiguracao){
         if(codigoTecla == KeyEvent.VK_ESCAPE){
            escPressionado = true;
         }
      }
   }

   @Override
   public void keyReleased(KeyEvent e){
      int codigoTecla = e.getKeyCode();

      if(painelJogo.estadoAtualJogo == painelJogo.estadoJogoExecutando){
         switch(codigoTecla){
            case KeyEvent.VK_W:
               wPressionado = false;
            break;
   
            case KeyEvent.VK_A:
               aPressionado = false;
            break;
   
            case KeyEvent.VK_S:
               sPressionado = false;
            break;
   
            case KeyEvent.VK_D:
               dPressionado = false;
            break;
   
         }
      }

      //logica invertida
      //trocar pra false no metodo quando for usado
      if(painelJogo.estadoAtualJogo == painelJogo.estadoFimJogo){
         switch(codigoTecla){
            case KeyEvent.VK_W:
               wPressionado = true;
            break;
   
            case KeyEvent.VK_S:
               sPressionado = true;
            break;

            case KeyEvent.VK_ENTER:
               enterPressionado = true;
            break;
         }
      }

      //logica invertida
      //trocar pra false no metodo quando for usado
      if(painelJogo.estadoAtualJogo == painelJogo.estadoTelaInicio){
         switch(codigoTecla){
            case KeyEvent.VK_W:
               wPressionado = true;
            break;
   
            case KeyEvent.VK_S:
               sPressionado = true;
            break;

            case KeyEvent.VK_ENTER:
               enterPressionado = true;
            break;
         }
      }

      if(painelJogo.estadoAtualJogo == painelJogo.estadoTelaConfiguracao){
         //logica invertida com excecao do esc
         switch(codigoTecla){
            case KeyEvent.VK_ESCAPE:
            escPressionado = false;
            break;
         
            case KeyEvent.VK_W:
               wPressionado = true;
            break;
         
            case KeyEvent.VK_A:
               aPressionado = true;
            break;

            case KeyEvent.VK_S:
               sPressionado = true;
            break;
      
            case KeyEvent.VK_D:
               dPressionado = true;
            break;
         }
      }
   }

   /**
    * @desc Apenas por precaução
    */
   public void zerarTeclas(){
      wPressionado = false;
      aPressionado = false;
      sPressionado = false;
      dPressionado = false;
      enterPressionado = false;

      setaCima = false;
      setaBaixo = false;
      
      escPressionado = false;
   }
   
}
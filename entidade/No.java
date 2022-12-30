package entidade;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Responsável por cada unidade do jogo, compartilhada
 * entre frutas e partes do corpo da cobrinha 
 */
public class No{
   public int x;
   public int y;
   public BufferedImage icone;
   public Rectangle hitbox;

   /**
    * 
    * @param x valor de posição X
    * @param y valor de posição Y
      @desc Gera um nó único que será posicionado de acordo com os valoes de X e Y
    */
   public No(int x, int y){
      this.x = x;
      this.y = y;
   }

   /**
    * 
    * @param tamanhoBloco Tamanho de cada bloco do jogo já contando que este valor está de acordo com o scaling do jogo
    * @desc Configura uma área virtual que simulará as colisões entre a fruta e a própria cobrinha durante o jogo
    */
   public void configurarHitbox(int tamanhoBloco){
      hitbox = new Rectangle(this.x, this.y, tamanhoBloco, tamanhoBloco);
   }
}

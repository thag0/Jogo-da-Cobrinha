import javax.swing.JFrame;
import render.PainelJogo;

class Main{
   public static void main(String[] args){
      JFrame tela = new JFrame();
      tela.setTitle("Jogo da Cobrinha");
      tela.setResizable(false);
      
      PainelJogo painelJogo = new PainelJogo();

      tela.add(painelJogo);
      tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
      tela.setIconImage(painelJogo.iconeJogo);

      tela.pack();
      tela.setLocationRelativeTo(null);

      tela.setVisible(true);
      painelJogo.startGameThread();
   }
}
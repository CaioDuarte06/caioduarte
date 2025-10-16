package application.view;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.image.*;

public class jogoController {

    @FXML
    private Canvas canva;

	private double playerX=200;
	private final double playerY=500;
	private final double raio=32;                 
	private final double larguraTela=360;
	private final double alturaTela=600;
	private int pontuacao=0;
	private ArrayList<Obstaculo> obstaculos= new ArrayList();
	private ArrayList<Tiro> tiros= new ArrayList();
	private Random random = new Random();
	private boolean esquerda,direita;
	private Image imagemPlayer;
	private Image imagemObstaculo;
	private int vida = 3;

	
	@FXML
	public void initialize() {
		imagemPlayer = new Image(getClass().getResourceAsStream("goku.jpg"));
        imagemObstaculo = new Image(getClass().getResourceAsStream("freeza.jpg"));

        canva.setFocusTraversable(true);
        canva.requestFocus();

        GraphicsContext gc = canva.getGraphicsContext2D();

        canva.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) esquerda = true;
            if (e.getCode() == KeyCode.RIGHT) direita = true;
            if (e.getCode() == KeyCode.SPACE) {
                tiros.add(new Tiro(playerX - 2.5, playerY - raio));
            }
            if (e.getCode() == KeyCode.SHIFT) { 
                
            }
        });

        canva.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) esquerda = false;
            if (e.getCode() == KeyCode.RIGHT) direita = false;
        });

        AnimationTimer timer = new AnimationTimer() {
            long lastSpawn = 0,lastSpawnTiro=0;
           

            @Override
            public void handle(long now) {
                atualizar();
                desenhar(gc);

                if (now - lastSpawn > 1_000_000_000) {
                   // obstaculos.add(new Obstaculo((larguraTela - 40) / 2, -40)); // obstáculo centralizado
                    obstaculos.add(new Obstaculo(random.nextInt((int) larguraTela - 40), -40));
                    lastSpawn = now;
                }
               
           //     if (now - lastSpawnTiro > 100_000_000) {
                    // obstaculos.add(new Obstaculo((larguraTela - 40) / 2, -40)); // obstáculo centralizado
                  //  tiros.add(new Tiro(playerX - 2.5, playerY - raio));
                 //    lastSpawnTiro = now;
               //  }
             
                if (vida <= 0) {
                    stop(); // para o jogo
                    gc.setFill(Color.RED);
                    gc.setFont(javafx.scene.text.Font.font(36));
                    gc.fillText("Game Over", larguraTela / 2 - 100, alturaTela / 2);
                }
               
            }
        };

        timer.start();
    }

    private void atualizar() {
        if (esquerda && playerX - raio > 0) playerX -= 5;
        if (direita && playerX + raio < larguraTela) playerX += 5;

        // Atualiza obstáculos
        Iterator<Obstaculo> itObs = obstaculos.iterator();
        while (itObs.hasNext()) {
            Obstaculo obs = itObs.next();
            obs.y += 2;

            if (obs.y > alturaTela) {
                itObs.remove();
            }
        }

        // Atualiza tiros
        Iterator<Tiro> itTiro = tiros.iterator();
        while (itTiro.hasNext()) {
            Tiro tiro = itTiro.next();
            tiro.y -= 8;

            if (tiro.y < 0) {
                itTiro.remove();
                continue;
            }

            // Verifica colisão com obstáculos
            Iterator<Obstaculo> itObsColisao = obstaculos.iterator();
            while (itObsColisao.hasNext()) {
                Obstaculo obs = itObsColisao.next();
                boolean colidiu = tiro.y <= obs.y + obs.altura &&
                                  tiro.y + tiro.altura >= obs.y &&
                                  tiro.x + tiro.largura >= obs.x &&
                                  tiro.x <= obs.x + obs.largura;

                if (colidiu) {
                    pontuacao++;
                    itObsColisao.remove();
                    itTiro.remove();
                    break;
                }
            }
        }
     // Verifica colisão entre Goku e obstáculos
        Iterator<Obstaculo> itObsVida = obstaculos.iterator();
        while (itObsVida.hasNext()) {
            Obstaculo obs = itObsVida.next();
            
            boolean colidiuComPlayer = playerY - raio < obs.y + obs.altura &&
                                       playerY + raio > obs.y &&
                                       playerX + raio > obs.x &&
                                       playerX - raio < obs.x + obs.largura;

            if (colidiuComPlayer) {
                vida--; // perde uma vida
                itObsVida.remove(); // remove o obstáculo
                break; // sai do loop para não perder várias vidas de uma vez
            }
        }

    }

    private void desenhar(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, larguraTela, alturaTela);
       
     // Estrelas
        gc.setFill(Color.WHITE);
        for (int i = 0; i < 50; i++) {
            double estrelaX = random.nextDouble() * larguraTela;
            double estrelaY = random.nextDouble() * alturaTela;
            gc.fillOval(estrelaX, estrelaY, 2, 2);
        }

        gc.drawImage(imagemPlayer, playerX - raio, playerY - raio, raio * 2, raio * 2);

        for (Obstaculo obs : obstaculos) {
            gc.drawImage(imagemObstaculo, obs.x, obs.y, obs.largura, obs.altura);
        }

        gc.setFill(Color.BLUE);
        for (Tiro tiro : tiros) {
            gc.fillRect(tiro.x, tiro.y, tiro.largura, tiro.altura);
        }

        gc.setFill(Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font(18));
        gc.fillText("Pontuação: " + pontuacao, 10, 20);
        
        gc.setFill(Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font(18));
        gc.fillText("Pontuação: " + pontuacao, 10, 20);
        gc.fillText("Vida: " + vida, 10, 40); // nova linha com vidas

    }

    // Classe de obstáculos
    class Obstaculo {
        double x, y;
        //final double largura = 60;
        //final double altura = 50;
       
        final double largura = 70;
        final double altura = 90;

        Obstaculo(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    // Classe de tiros
    class Tiro {
        double x, y;
        final double largura = 5;
        final double altura = 10;

        Tiro(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
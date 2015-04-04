package dojoparticoes;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/* @authors Carlos Daniel, Fernando D., João M., Jonatha N., Monique M.
Algoritmo escolhido: Selecao com Substituicao
*/

public class GeracaoParticoes {

    /**
     * Executa o algoritmo de geração de partições por Classificação Interna
     *
     * @param nomeArquivoEntrada arquivo de entrada
     * @param nomeArquivoSaida array list contendo os nomes dos arquivos de
     * saída a serem gerados
     * @param M tamanho do array em memória para manipulação dos registros
     */
    public void executaClassificacaoInterna(String nomeArquivoEntrada, List<String> nomeArquivoSaida, int M) throws Exception {

        //TODO: Inserir aqui o código do algoritmo de geração de partições
    }

    /**
     * Executa o algoritmo de geração de partições por Seleção com Substituição
     *
     * @param nomeArquivoEntrada arquivo de entrada
     * @param nomeArquivoSaida array list contendo os nomes dos arquivos de
     * saída a serem gerados
     * @param M tamanho do array em memória para manipulação dos registros
     */
    public void executaSelecaoComSubstituicao(String nomeArquivoEntrada, List<String> nomeArquivoSaida, int M) throws Exception {
        Cliente monique;
        DataInputStream leitor = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeArquivoEntrada)));
        DataOutputStream particao = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nomeArquivoSaida.get(0))));

        try {

            ArrayList<Cliente> memoria = new ArrayList<Cliente>();
            ArrayList<Boolean> congelado = new ArrayList<Boolean>();
            int HIV;
            boolean itsOver = false;
            for (int i = 0; i < M; i++) {
                HIV = leitor.readInt();
                if (HIV == Integer.MAX_VALUE) {
                    itsOver = true;
                    break;
                }
                monique = new Cliente(HIV, leitor.readUTF());
                memoria.add(monique);
                congelado.add(false);
            }
            int nParte = 0;
            particao = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nomeArquivoSaida.get(nParte))));

            if (memoria.isEmpty()) {
                monique = new Cliente(Integer.MAX_VALUE, "");
                monique.salva(particao);
            }
            while (!memoria.isEmpty()) {

                boolean todosCongelados = true;
                for (int i = 0; i < congelado.size(); i++) {
                    if (congelado.get(i) == false) {
                        todosCongelados = false;
                        i=congelado.size()+1;
                    }

                }

                if (todosCongelados) {
                    monique = new Cliente(Integer.MAX_VALUE, "");
                    monique.salva(particao);
                    nParte++;
                    particao.close();
                    particao = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nomeArquivoSaida.get(nParte))));
                    for (int i = 0; i < congelado.size(); i++) {
                        congelado.set(i, false);
                    }
                }
                int menor = Integer.MAX_VALUE;
                int posicao = 0;
                for (int i = 0; i < memoria.size(); i++) {
                    if (congelado.get(i) == false) {
                        if (memoria.get(i).codCliente < menor) {
                            menor = memoria.get(i).codCliente;
                            posicao = i;
                        }
                    }
                }
                
                memoria.get(posicao).salva(particao);
                int codSalvo = memoria.get(posicao).codCliente;
                congelado.remove(posicao);
                memoria.remove(posicao);
                //Esta removendo quem foi escrito antes de comparar quem entrou
                if (!itsOver) {
                    
                    HIV = leitor.readInt();
                    if (HIV != Integer.MAX_VALUE) {
                        monique = new Cliente(HIV, leitor.readUTF());
                        if (codSalvo > monique.codCliente) {
                            memoria.add(monique);
                            congelado.add(true);
                        }
                        //comparar se monique<posicao para congelar ou nao
                        else{
                            memoria.add(monique);
                            congelado.add(false);
                        }
                    } else {
                        itsOver = true;

                    }

                }

                if (memoria.isEmpty()) {
                    monique = new Cliente(Integer.MAX_VALUE, "");
                    monique.salva(particao);
                    
                }
            }

        } finally {
            if (leitor != null) {
                leitor.close();
            }
            if (particao != null) {
                particao.close();
            }
        }

        //lembrar de fechar partiçoes
    }
}

/**
 * Executa o algoritmo de geração de partições por Seleção Natural
 *
 * @param nomeArquivoEntrada arquivo de entrada
 * @param nomeArquivoSaida array list contendo os nomes dos arquivos de saída a
 * serem gerados
 * @param M tamanho do array em memória para manipulação dos registros
 * @param n tamanho do reservatório
 */


//    public void executaSelecaoNatural(String nomeArquivoEntrada, List<String> nomeArquivoSaida, int M, int n) throws Exception {
//
//            //TODO: Inserir aqui o código do algoritmo de geração de partições
//    }


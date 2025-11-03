package Trabalho;

import java.util.ArrayList;
import java.util.Scanner;

import Trabalho.objetos.Produto;

public class Principal {
	
	static Scanner scan = new Scanner(System.in);
	static ArrayList<Produto>loja = new ArrayList<>();
	static int nota50 = 5;
	static int nota20 = 5;
	static int nota10 = 0;
	static int antiga50=nota50;
	static int antiga20=nota20;
	static int antiga10=nota10;
	static int saldo = ((nota50*50)+(nota20*20)+(nota10*10));
	
	public static void main(String[] args) {
		loja.add(new Produto("coca", 10, 40));
		loja.add(new Produto("cama", 1000, 20));
		mostrarMenu();
	}
	public static void mostrarMenu() {
		int escolha = 0;
		do {
			System.out.println("-----menu-----");
			System.out.println("1 - cadastrar produto");
			System.out.println("2 - listar prdutos");
			System.out.println("3 - realizar venda");	
			System.out.println("4 - Exibir saldo do caixa e quantidade de notas");
			System.out.println("5 - sair");
			escolha = scan.nextInt();
			scan.nextLine();
			verificarEscolha(escolha);
		}while(escolha!=5);
	}
	public static void verificarEscolha(int escolha){
		switch (escolha) {
		case 1: {
			cadastrarProduto();
			break;
		}
		case 2: {
			listarProdutos();
			break;
		}
		case 3: {
			realizarVenda();
			break;
		}
		case 4: {
			exibirCaixa();
			break;
		}
		default:
			if(escolha!=5) {
				System.out.println("selecione uma opcao valida");
			}
		}
	}
	public static void cadastrarProduto() {
		Produto produto = new Produto();
		System.out.println("digite o nome do produto");
		String nome = scan.nextLine();
		for (int i = 0; i < loja.size(); i++) {
			if(loja.get(i).getNome().equals(nome)) {
				System.out.println("esse produto ja existe, digite o estoque para adicionar");
				int adicionado = scan.nextInt();
				scan.nextLine();
				loja.get(i).setEstoque(loja.get(i).getEstoque()+adicionado);
				return;
			}
		}
		produto.setNome(nome);
		System.out.println("digite o valor do produto");
		produto.setPreco(scan.nextDouble());
		scan.nextLine();
		System.out.println("digite o estoque do produto");
		produto.setEstoque(scan.nextInt());
		scan.nextLine();
		loja.add(produto);
	}
	public static void listarProdutos() {
		for (int i = 0; i < loja.size(); i++) {
			System.out.printf("%d - ",i+1);
			loja.get(i).mostrarProduto();
		}
	}
	public static void realizarVenda() {
		listarProdutos();
		System.out.println("digite o numero do produto que deseja comprar :");
		int escolha = scan.nextInt();
		scan.nextLine();
		System.out.println("digite a quantidade que deseja comprar: ");
		int quantidade = scan.nextInt();
		scan.nextLine();
		if (quantidade>loja.get(escolha-1).getEstoque()) {
			System.out.println("a quantidade digitada e maior doque a disponivel, tente novamente: ");
			return;
		}
		System.out.println("voce devera pagar: "+loja.get(escolha-1).getPreco()*quantidade);
		int dado = pedirDinheiro(loja.get(escolha-1).getPreco()*quantidade);
		boolean verificacao = calcularTroco(dado-(loja.get(escolha-1).getPreco()*quantidade));
		if (verificacao==true) {
			loja.get(escolha-1).setEstoque(loja.get(escolha-1).getEstoque()-quantidade);
			atualizarAntiga();
		}
		if (verificacao==false) {
			removerNotas();
		}
		if (loja.get(escolha-1).getEstoque()==0) {
			loja.remove(escolha-1);
		}
	}
	public static int pedirDinheiro(double pagar) {
		System.out.println("digite a quatidade de notas de 50 que voce ira dar");
		int n5 = scan.nextInt();
		scan.nextLine();
		System.out.println("digite a quatidade de notas de 20 que voce ira dar");
		int n2 = scan.nextInt();
		scan.nextLine();
		System.out.println("digite a quatidade de notas de 10 que voce ira dar");
		int n1 = scan.nextInt();
		scan.nextLine();
		System.out.printf("voce entregou %d para o caixa \n",((n5*50)+(n2*20)+(n1*10)));
		nota50+=n5;
		nota20+=n2;
		nota10+=n1;
		return ((n5*50)+(n2*20)+(n1*10));
	}
	public static boolean calcularTroco(double valor) {
		double troco = valor;
		if(valor>saldo) {
			System.out.println("infelizmente nao temos troco suficiente :( ");
			return false;
		}
		if ((valor/50>=1) && (nota50==0)) {
			System.out.println("infelizmente nao temos troco suficiente :( ");
			return false;
		}
		int n5 = (int) (valor/50);
		if(n5>nota50) {
			n5=n5-(n5-nota50);
		}
		valor=valor-(n5*50);
		if ((valor/20>=1) && (nota20==0)) {
			System.out.println("infelizmente nao temos troco suficiente :( ");
			return false;
		}	
		int n2 = (int) (valor/20);
		if(n2>nota20) {
			n2=n2-(n2-nota20);
		}
		valor=valor-(n2*20);
		if ((valor/10>=1) && (nota10==0)) {
			System.out.println("infelizmente nao temos troco suficiente :( ");
			return false;
		}
		int n1 = (int) (valor/10);
		if(n1>nota10) {
			n1=n1-(n1-nota10);
		}
		saldo = ((nota50*50)+(nota20*20)+(nota10*10));
		nota50-=n5;
		nota20-=n2;
		nota10-=n1;
		System.out.printf("serao usadas: \n %d notas de 50 \n %d notas de 20 \n %d notas de 10 \n",n5,n2,n1);
		System.out.println("o total do troco vale: "+troco);
		saldo-=troco;
		return true;
	}
	public static void atualizarAntiga() {
		antiga50=nota50;
		antiga20=nota20;
		antiga10=nota10;
	}
	public static void removerNotas() {
		nota50=antiga50;
		nota20=antiga20;
		nota10=antiga10;
	}
	public static void exibirCaixa() {
		System.out.printf("o saldo: %d \n notas de 50: %d \n notas de 20: %d \n notas de 10: %d \n",saldo,nota50,nota20,nota10);
	}

}

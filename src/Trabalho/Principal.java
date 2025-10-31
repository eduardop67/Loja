package Trabalho;

import java.util.ArrayList;
import java.util.Scanner;

import Trabalho.objetos.Produto;

public class Principal {
	
	static Scanner scan = new Scanner(System.in);
	static ArrayList<Produto>loja = new ArrayList<>();
	static int nota50 = 5;
	static int nota20 = 5;
	static int nota10 = 5;
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
		calcularTroco(dado-(loja.get(escolha-1).getPreco()*quantidade));
		loja.get(escolha-1).setEstoque(loja.get(escolha-1).getEstoque()-quantidade);
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
	public static void calcularTroco(double valor) {
		saldo = ((nota50*50)+(nota20*20)+(nota10*10));
		if(valor>saldo) {
			System.out.println("infelizmente nao temos troco suficiente :( ");
			return;
		}
		double troco = valor;
		int n5 = (int) (valor/50);
		if(n5>nota50) {
			n5=n5-(n5-nota50);
		}
		nota50-=n5;
		valor=valor-(n5*50);
		int n2 = (int) (valor/20);
		if(n2>nota20) {
			n2=n2-(n2-nota20);
		}
		nota20-=n2;
		valor=valor-(n2*20);
		int n1 = (int) (valor/10);
		if(n1>nota10) {
			n1=n1-(n1-nota10);
		}
		nota10-=n1;
		System.out.printf("serao usadas: \n %d notas de 50 \n %d notas de 20 \n %d notas de 10 \n",n5,n2,n1);
		System.out.println("o total do troco vale: "+troco);
		saldo-=troco;
	}
	public static void exibirCaixa() {
		System.out.printf("o saldo: %d \n notas de 50: %d \n notas de 20: %d \n notas de 10: %d \n",saldo,nota50,nota20,nota10);
	}

}

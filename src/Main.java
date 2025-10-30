import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // criação de pessoa
        System.out.println("Digite o nome do titular: ");
        String nome = scanner.nextLine();
        System.out.println("Digite a idade: ");
        int idade = scanner.nextInt();
        System.out.println("Digite o sexo (M/F): ");
        char sexo = scanner.next().charAt(0);
        Pessoa pessoa = new Pessoa(nome, idade, sexo);

        // criando a conta com depósito
        System.out.println("Digite o número da conta: ");
        int numConta = scanner.nextInt();
        System.out.println("Digite o depósito inicial: ");
        float depositoInicial = scanner.nextFloat();
        ContaBancaria conta = new ContaBancaria(pessoa, numConta, depositoInicial);

        // menu bancário para testes automatizados
        int opcao;
        do {
            System.out.println("\n--- Menu da Conta Bancária ---");
            System.out.println("1. Consultar Saldo");
            System.out.println("2. Consultar Cheque Especial");
            System.out.println("3. Depositar Dinheiro");
            System.out.println("4. Sacar Dinheiro");
            System.out.println("5. Pagar Boleto");
            System.out.println("6. Verificar se está usando Cheque Especial");
            System.out.println("7. Fechar Conta");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    conta.consultarSaldo();
                    break;
                case 2:
                    conta.consultarChequeEspecial();
                    break;
                case 3:
                    System.out.print("Valor a depositar: ");
                    float dep = scanner.nextFloat();
                    conta.depositar(dep);
                    break;
                case 4:
                    System.out.print("Valor a sacar: ");
                    float sac = scanner.nextFloat();
                    conta.sacar(sac);
                    break;
                case 5:
                    System.out.print("Valor do boleto: ");
                    float bol = scanner.nextFloat();
                    conta.pagarBoleto(bol);
                    break;
                case 6:
                    conta.verificarSeUsandoChequeEspecial();
                    break;
                case 7:
                    conta.fecharConta();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }
}


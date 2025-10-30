import java.util.Scanner;

public class ContaBancaria {
    private Pessoa titular;
    private int numConta;
    private float saldo;
    private float chequeEspecial;
    private float chequeInicial;
    private boolean isAberta;
    Scanner leitor = new Scanner(System.in);

    // construtor > criando a conta com depósito inicial + calculo de cheque especial.
    public ContaBancaria(Pessoa titular, int numConta, float depositoInicial) {
        this.titular = titular;
        this.numConta = numConta;
        this.isAberta = true;
        this.saldo = depositoInicial;

        // calculo do cheque especial conforme as regras estabelecidas.
        if (depositoInicial <= 500.00f) {
            this.chequeEspecial = 50.00f;
        } else {
            this.chequeEspecial = depositoInicial * 0.50f;
        }
        this.chequeInicial = this.chequeEspecial;
        System.out.println("Conta criada com sucesso para " + titular.getNome() + "!");
        System.out.println("Saldo inicial: R$" + getSaldo());
        System.out.println("Cheque especial: R$" + getChequeEspecial());
    }

    // getters e setters
    public Pessoa getTitular() {
        return titular;
    }

    public void setTitular(Pessoa titular) {
        this.titular = titular;
    }

    public int getNumConta() {
        return numConta;
    }

    public void setNumConta(int numConta) {
        this.numConta = numConta;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public boolean isAberta() {
        return isAberta;
    }

    public void setAberta(boolean aberta) {
        isAberta = aberta;
    }

    public float getChequeEspecial() {
        return chequeEspecial;
    }

    public void setChequeEspecial(float chequeEspecial) {
        this.chequeEspecial = chequeEspecial;
    }

    public float getChequeInicial() {
        return chequeInicial;
    }

    public void setChequeInicial(float chequeInicial) {
        this.chequeInicial = chequeInicial;
    }

    // Métodos
    public void abrirConta() {
        if (!isAberta) {
            isAberta = true;
            System.out.println("Conta reaberta com sucesso!");
        } else {
            System.out.println("Conta já está aberta.");
        }
    }

    public void fecharConta() {
        if (isAberta && saldo == 0 && chequeEspecial == chequeInicial) { // só fecha se não houver dívidas
            isAberta = false;
            System.out.println("Conta fechada com sucesso!");
        } else {
            System.out.println("Não é possível fechar a conta: saldo pendente ou cheque especial em uso.");
        }
    }

    // cobrar taxa de 20% assim que possível, se usado cheque especial.
    private void cobrarTaxaChequeEspecial() {
        float valorUsado = chequeInicial - chequeEspecial;
        if (valorUsado > 0) {
            float taxa = valorUsado * 0.20f;
            saldo -= taxa;
            System.out.println("Taxa de cheque especial cobrada: R$" + taxa +
                    " (20% sobre R$" + valorUsado + " usado).");
            chequeEspecial = chequeInicial; // reset do uso após cobrança
        }
    }

    public void consultarSaldo() {
        if (!isAberta) {
            System.out.println("Conta fechada, não é possível consultar saldo!\n" +
                    "Abra uma conta.");
            return;
        }
        System.out.println("Saldo atual: R$" + getSaldo());
    }

    public void consultarChequeEspecial() {
        if (!isAberta) {
            System.out.println("Conta fechada, não é possível consultar o cheque especial em uso.\n" +
                    "Abra uma conta.");
        } else {
            System.out.println("Cheque Especial disponível: R$" + getChequeEspecial());
        }
    }

    public void verificarSeUsandoChequeEspecial() {
        if (!isAberta) {
            System.out.println("Conta inativa.");
            return;
        }
        float valorUsado = chequeInicial - chequeEspecial;
        if (valorUsado > 0) {
            System.out.println("A conta está usando cheque especial (R$" + valorUsado + " usado).");
            cobrarTaxaChequeEspecial(); // Cobra taxa assim que possível
        } else {
            System.out.println("A conta não está usando cheque especial.");
        }
    }

    public void pagarBoleto(float valorBoleto) {
        if (!isAberta) {
            System.out.println("Conta fechada! Não é possível realizar pagamentos.");
            return;
        }
        if (valorBoleto <= 0) {
            System.out.println("Valor inválido!");
            return;
        }
        if (valorBoleto <= saldo) {
            saldo -= valorBoleto;
            System.out.println("Pagamento do boleto realizado com sucesso!");
        } else {
            float necessario = valorBoleto - saldo;
            if (necessario <= chequeEspecial) {
                chequeEspecial -= necessario;
                saldo = 0;
                System.out.println("Pagamento realizado usando cheque especial!");
            } else {
                System.out.println("Saldo e limite do cheque especial insuficientes para pagar o boleto.");
                return;
            }
        }
        consultarSaldo();
        consultarChequeEspecial();
    }

    public void depositar(float valor) {

        if (!isAberta) {
            System.out.println("Conta inativa, não é possível depositar.");
            return;
        }
        if (valor <= 0) {
            System.out.println("Valor inválido! Digite um valor maior que R$0,00.");
            return;
        }
        saldo += valor;
        System.out.println("Depósito de R$" + valor + " realizado com sucesso!");
        consultarSaldo();
        cobrarTaxaChequeEspecial(); // cobra taxa se o cheque especial foi usado
    }

    public void sacar(float valor) {
        if (!isAberta) {
            System.out.println("Conta inativa, não é possível sacar.");
            return;
        }
        if (valor <= 0) {
            System.out.println("Valor inválido! Digite um valor acima de R$0,00.");
            return;
        }
        if (valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque de R$" + valor + " realizado com sucesso!");
        } else {
            float necessario = valor - saldo;
            if (necessario <= chequeEspecial) {
                chequeEspecial -= necessario;
                saldo = 0; // Saldo vai a zero
                System.out.println("Saque realizado utilizando cheque especial!");
            } else {
                System.out.println("Saldo e limite insuficientes para saque.");
                return;
            }
        }
        consultarSaldo();
        consultarChequeEspecial();

    }
}

package visao;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



import controle.ProcesssMnutencoes;
import modelo.DAO;
import modelo.Manutencao;

public class FormManutencoes extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel painel;
	
	private JComboBox<String> Equipamentos;
	private String texto = "";
	private JLabel id, data, equipamento , custoHora, tempGasto; 
	private JTextField tfId,tfdata, tfcustoHora, tftempGasto;
	private JTextArea Tela;
	private JButton cadastrar, buscar, deletar, alterar;
	private int autoId = ProcesssMnutencoes.manutencoes.size() + 1;
	private String data2 = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
	
	
	FormManutencoes(){
		setTitle("Formulario de Manuten??o");
		setBounds(400, 200,750, 500);
		painel = new JPanel();
		painel.setBackground(new Color(255,242,166));
		setContentPane(painel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);

		id = new JLabel("Id:");
		id.setBounds(10,35,64, 14);
		painel.add(id);
		data = new JLabel("Data:");
	    data.setBounds(10,85,50, 14);
	    painel.add(data);
	    equipamento = new JLabel("Equipamentos:");
	    equipamento.setBounds(10,130,100,14);
	    painel.add(equipamento);
	    Equipamentos = new JComboBox<String>(new String[] { "Esteira", "bra?o hidraulico","Impressora" });
	    Equipamentos.setBounds(100,125,90, 25);
	    painel.add(Equipamentos);
	    
	    custoHora = new JLabel("Custo de hora:");
	    custoHora.setBounds(10,165, 100, 14);
	    painel.add(custoHora);
	    tempGasto = new JLabel("Tempo Gasto:");
	    tempGasto.setBounds(10,205, 100, 14);
	    painel.add(tempGasto);
	    
	    tfId = new JTextField(String.format("%d", autoId));
		tfId.setEditable(false);
		tfId.setBounds(40,30,86,25);
		painel.add(tfId);
		tfdata = new JTextField(data2);
		tfdata.setBounds(50,80,86,25);
		tfdata.setEditable(false);
		painel.add(tfdata);
		tfcustoHora = new JTextField();
		tfcustoHora.setBounds(100,162,86,25);
		painel.add(tfcustoHora);
		tftempGasto = new JTextField();
		tftempGasto.setBounds(100,200,86,25);
		painel.add(tftempGasto);
		
		Tela = new JTextArea();
		Tela.setBounds(50,250, 620, 200);
		Tela.setEnabled(true);
		Tela.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.pink));
		painel.add(Tela);
		preencherAreaDeTexto();
		

		cadastrar = new JButton("Cadastrar");
		buscar = new JButton("Buscar");
		alterar = new JButton("Alterar");
		deletar = new JButton("Apagar");
		cadastrar.setBounds(455, 55, 110, 30);
		buscar.setBounds(455, 100, 110, 30);
		deletar.setBounds(455, 150, 110, 30);
		alterar.setBounds(455, 200, 110, 30);
		alterar.setEnabled(false);
		deletar.setEnabled(false);
		
		painel.add(cadastrar);
		painel.add(deletar);
		painel.add(alterar);
		painel.add(buscar);
	 
		
		cadastrar.addActionListener(this);
		alterar.addActionListener(this);
		deletar.addActionListener(this);
		buscar.addActionListener(this);
}
	
	
	private void preencherAreaDeTexto() {
		texto = "";
		for (Manutencao p : ProcesssMnutencoes.manutencoes) {
			texto += p.toString()+"\n";
		}
		Tela.setText(texto);
	}
	
	
	int obterIndice(String item) {
		switch (item) {
		case "Id":
			return 0;
		case "Data":
			return 1;
		case "Equipamento":
			return 2;
		case "Custo e hora":
			return 3;
		case "Tempo Gasto":
			return 4;
		default:
			return -1;
		}
	}
	
	private void cadastrar() {
  String data = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		
		if (tfcustoHora.getText().length() !=0 && tftempGasto.getText().length() !=0) {
			
			ProcesssMnutencoes.manutencoes.add(new Manutencao(autoId , data, Equipamentos.getSelectedItem().toString(),
			Double.parseDouble(tfcustoHora.getText().toString()),Double.parseDouble(tftempGasto.getText())));
			  autoId++;
		} else {
			JOptionPane.showMessageDialog(this, "Favor Preencher todos as informa??es");
		}
		limparCampos();
		preencherAreaDeTexto();
		ProcesssMnutencoes.salvar();
	}
	
	private void limparCampos() {
		tfcustoHora.setText(null);
		tftempGasto.setText(null);
		
	}
 
	private void deletar() {
		 int id = Integer.parseInt(tfId.getText());
		 int indice = -1;
		for(Manutencao m : ProcesssMnutencoes.manutencoes) {
			if (m.getId() == id) {
				indice = ProcesssMnutencoes.manutencoes.indexOf(m);
	}
		}
		ProcesssMnutencoes.manutencoes.remove(indice);
		preencherAreaDeTexto();
		limparCampos();
		cadastrar.setEnabled(false);
		alterar.setEnabled(false);
		deletar.setEnabled(true);
		ProcesssMnutencoes.salvar();
		tfId.setText(String.format("%d", ProcesssMnutencoes.manutencoes.size() + 1));
		
	}
	
	private void buscar() {
		String entrada = JOptionPane.showInputDialog( this,"Digite o id da manuten??o");
	
		boolean num = true;
		if(entrada != null) {
			for (int i = 0; i < entrada.length(); i++) {
				if(!Character.isDigit(entrada.charAt(i))) {
					num = false;
				}
			}
			
		}else {
			num = false;
		}
		if (num) {
			int id = Integer.parseInt(entrada);
			
			boolean achou = false;
			for (Manutencao manu : ProcesssMnutencoes.manutencoes) {
				if (manu.getId() == id) {
					achou = true;
					int indice = ProcesssMnutencoes.manutencoes.indexOf(manu);
					tfId.setText(ProcesssMnutencoes.manutencoes.get(indice).getId("s"));
					tfdata.setText(ProcesssMnutencoes.manutencoes.get(indice).getData());
					Equipamentos.setSelectedIndex(obterIndice(ProcesssMnutencoes.manutencoes.get(indice).getEquipamento()));
					tfcustoHora.setText(ProcesssMnutencoes.manutencoes.get(indice).getCustoHora("s"));
					tftempGasto.setText(ProcesssMnutencoes.manutencoes.get(indice).getTempoGasto("s"));
					ProcesssMnutencoes.salvar();
					cadastrar.setEnabled(false);
					alterar.setEnabled(true);
					deletar.setEnabled(true);
					break;
				}
			}
			
			if (!achou) {
				JOptionPane.showMessageDialog(this, "N?o encontrado");
			}
		}
	}
	private void alterar() {
		String data = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		int id = Integer.parseInt(tfId.getText());
		int indice = -1;
		
		for(Manutencao menu : ProcesssMnutencoes.manutencoes) {
			indice = ProcesssMnutencoes.manutencoes.indexOf(menu);
			
		}
		if(tfcustoHora.getText().length() !=0 && tftempGasto.getText().length() !=0){
			
			ProcesssMnutencoes.manutencoes.set(indice, new Manutencao(id, data, Equipamentos.getSelectedItem().toString(),
					Double.parseDouble(tfcustoHora.getText().toString()),Double.parseDouble(tftempGasto.getText())));
			preencherAreaDeTexto();
			limparCampos();
			
		
	} else {
		JOptionPane.showMessageDialog(this, "Favor preencher todos os campos.");
	}
	cadastrar.setEnabled(false);
	alterar.setEnabled(true);
	deletar.setEnabled(false);
	tfId.setText(String.format("%d", ProcesssMnutencoes.manutencoes.size() + 1));
	ProcesssMnutencoes.salvar();
}
		


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cadastrar) {
			cadastrar();
		}
		if (e.getSource() == deletar) {
			deletar();
		}
		if(e.getSource() == buscar) {
			buscar();
		}
		if(e.getSource() == alterar) {
			alterar();
		}
	}
	public static void main(String[] args) {
		 new
		 FormManutencoes().setVisible(true); 
		ProcesssMnutencoes.abrir();
	}


}

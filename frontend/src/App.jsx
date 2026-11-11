import { useEffect, useState } from "react";

const API = "http://localhost:8085/alunos";
const vazio = { nome: "", email: "", idade: "" };

export default function App() {
  const [alunos, setAlunos] = useState([]);
  const [dados, setDados] = useState(vazio);
  const [idEditando, setIdEditando] = useState(null);
  const [erro, setErro] = useState("");

  async function listar() {
    try {
      const resposta = await fetch(API);
      if (!resposta.ok) throw new Error();
      setAlunos(await resposta.json());
    } catch {
      setErro("Não foi possível conectar na API da porta 8085.");
    }
  }

  useEffect(() => {
    listar();
  }, []);

  function mudarCampo(evento) {
    setDados({ ...dados, [evento.target.name]: evento.target.value });
  }

  async function salvar(evento) {
    evento.preventDefault();
    setErro("");

    const editando = idEditando !== null;
    const resposta = await fetch(editando ? `${API}/${idEditando}` : API, {
      method: editando ? "PUT" : "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ ...dados, idade: Number(dados.idade) }),
    });

    if (!resposta.ok) {
      setErro("Não foi possível salvar o aluno.");
      return;
    }

    cancelar();
    listar();
  }

  function editar(aluno) {
    setIdEditando(aluno.id);
    setDados({ nome: aluno.nome, email: aluno.email, idade: aluno.idade });
    setErro("");
  }

  async function excluir(id) {
    if (!window.confirm("Excluir este aluno?")) return;
    await fetch(`${API}/${id}`, { method: "DELETE" });
    listar();
  }

  function cancelar() {
    setIdEditando(null);
    setDados(vazio);
  }

  return (
    <main>
      <h1>Cadastro de CARROS</h1>

      <form onSubmit={salvar}>
        <input
        
          name="nome"
          placeholder="Nome"
          value={dados.nome}
          onChange={mudarCampo}
          required
        />
          <input
          name="CPF"
          placeholder="CPF"
          value={dados.CPF}
          onChange={mudarCampo}
          required
        />
        <input
          name="email"
          type="email"
          placeholder="E-mail"
          value={dados.email}
          onChange={mudarCampo}
          required
        />
        <input
          name="idade"
          type="number"
          min="0"
          placeholder="Idade"
          value={dados.idade}
          onChange={mudarCampo}
          required
        />
        <button>{idEditando !== null ? "Salvar edição" : "Cadastrar"}</button>
        {idEditando !== null && (
          <button type="button" className="secundario" onClick={cancelar}>
            Cancelar
          </button>
        )}
      </form>

      {erro && <p className="erro">{erro}</p>}

      <table>
        <thead>
          <tr>
            <th>Nome</th>
            <th>E-mail</th>
            <th>Idade</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {alunos.map((aluno) => (
            <tr key={aluno.id}>
              <td>{aluno.nome}</td>
              <td>{aluno.email}</td>
              <td>{aluno.idade}</td>
              <td className="acoes">
                <button onClick={() => editar(aluno)}>Editar</button>
                <button className="excluir" onClick={() => excluir(aluno.id)}>
                  Excluir
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </main>
  );
}

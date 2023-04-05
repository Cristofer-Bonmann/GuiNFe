
--trigger para fazer histório de atualizaçõesm na tabela 'schema_nfe'
--executar cada um separadamente
CREATE TRIGGER atualiza_historico_campo
BEFORE UPDATE
OF campo
ON schema_nfe
BEGIN
	INSERT INTO historico (nota_tecnica, id_grupo, campo, valor) VALUES(OLD.nota_tecnica, OLD.idGrupo, 'campo', OLD.campo);
END

CREATE TRIGGER atualiza_historico_descricao
BEFORE UPDATE
OF descricao
ON schema_nfe
BEGIN
	INSERT INTO historico (nota_tecnica, id_grupo, campo, valor) VALUES(OLD.nota_tecnica, OLD.idGrupo, 'descricao', OLD.descricao);
END

CREATE TRIGGER atualiza_historico_elemento
BEFORE UPDATE
OF elemento
ON schema_nfe
BEGIN
	INSERT INTO historico (nota_tecnica, id_grupo, campo, valor) VALUES(OLD.nota_tecnica, OLD.idGrupo, 'elemento', OLD.elemento);
END

CREATE TRIGGER atualiza_historico_idGrupo
BEFORE UPDATE
OF idGrupo
ON schema_nfe
BEGIN
	INSERT INTO historico (nota_tecnica, id_grupo, campo, valor) VALUES(OLD.nota_tecnica, OLD.idGrupo, 'idGrupo', OLD.idGrupo);
END

CREATE TRIGGER atualiza_historico_observacao
BEFORE UPDATE
OF observacao
ON schema_nfe
BEGIN
	INSERT INTO historico (nota_tecnica, id_grupo, campo, valor) VALUES(OLD.nota_tecnica, OLD.idGrupo, 'observacao', OLD.observacao);
END

CREATE TRIGGER atualiza_historico_ocorrencia
BEFORE UPDATE
OF ocorrencia
ON schema_nfe
BEGIN
	INSERT INTO historico (nota_tecnica, id_grupo, campo, valor) VALUES(OLD.nota_tecnica, OLD.idGrupo, 'ocorrencia', OLD.ocorrencia);
END

CREATE TRIGGER atualiza_historico_pai
BEFORE UPDATE
OF pai
ON schema_nfe
BEGIN
	INSERT INTO historico (nota_tecnica, id_grupo, campo, valor) VALUES(OLD.nota_tecnica, OLD.idGrupo, 'pai', OLD.pai);
END

CREATE TRIGGER atualiza_historico_tamanho
BEFORE UPDATE
OF tamanho
ON schema_nfe
BEGIN
	INSERT INTO historico (nota_tecnica, id_grupo, campo, valor) VALUES(OLD.nota_tecnica, OLD.idGrupo, 'tamanho', OLD.tamanho);
END

CREATE TRIGGER atualiza_historico_tipo
BEFORE UPDATE
OF tipo
ON schema_nfe
BEGIN
	INSERT INTO historico (nota_tecnica, id_grupo, campo, valor) VALUES(OLD.nota_tecnica, OLD.idGrupo, 'tipo', OLD.tipo);
END
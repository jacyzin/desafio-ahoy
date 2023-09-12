package br.com.ahoy.xmluploader.adapter.in.rest;

import br.com.ahoy.xmluploader.domain.model.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

public class AgenteHandler extends DefaultHandler {
    private static final String AGENTES = "agentes";
    private static final String AGENTE = "agente";
    private static final String CODIGO = "codigo";
    private static final String DATA = "data";
    private static final String REGIAO = "regiao";
    private static final String GERACAO = "geracao";
    private static final String COMPRA = "compra";
    private static final String PRECOMEDIO = "precoMedio";
    private static final String VALOR = "valor";
    @Getter
    private Collection<Agente> agentes;
    @Getter
    private Collection<Regiao> regioes = new ArrayList<>();

    @Getter
    private Map<String, Collection<String>> valores = new HashMap<>();
    private Map<String, Boolean> status = new HashMap<>();
    private Regiao regiaoAtual;
    private ValoresBase geracaoAtual;
    private ValoresBase compraAtual;

    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        agentes = new ArrayList<Agente>();
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        switch (qName) {
            case AGENTES -> agentes = new ArrayList<>();
            case AGENTE -> agentes.add(new Agente());
            case CODIGO, VALOR, DATA -> elementValue = new StringBuilder();
            case REGIAO -> {
                regiaoAtual = new Regiao();
                regiaoAtual.setSigla(attr.getValue("sigla"));
                if (ultimoAgente().getRegiaoList() == null) { ultimoAgente().setRegiaoList(new ArrayList<>()); }
                ultimoAgente().getRegiaoList().add(regiaoAtual);
            }
            case GERACAO -> {
                valores.put(GERACAO, new ArrayList<>());
                status.put(GERACAO, true);
                regiaoAtual.setGeracao(geracaoAtual = new Geracao());
            }
            case COMPRA -> {
                valores.put(COMPRA, new ArrayList<>());
                status.put(COMPRA, true);
                regiaoAtual.setCompra(compraAtual = new Compra());
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case CODIGO -> ultimoAgente().setCodigo(elementValue.toString());
            case DATA -> ultimoAgente().setData(elementValue.toString());
            case GERACAO -> setElement(GERACAO, geracaoAtual);
            case COMPRA -> setElement(COMPRA, compraAtual);
            case VALOR -> determinaValor();
        }
    }

    private void determinaValor() {
        if (status.get(GERACAO)) {
            valores.get(GERACAO).add(elementValue.toString());
        } else if (status.get(COMPRA)) {
            valores.get(COMPRA).add(elementValue.toString());
        }
    }

    private Agente ultimoAgente() {
        List<Agente> agentes = (List<Agente>) getAgentes();
        int ultimoAgenteIndice = getAgentes().size() - 1;
        return agentes.get(ultimoAgenteIndice);
    }

    private void setElement(String elementType, ValoresBase element) {
        status.put(elementType, false);
        element.setValores(valores.get(COMPRA));
    }
}

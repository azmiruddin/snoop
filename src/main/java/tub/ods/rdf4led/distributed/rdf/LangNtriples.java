package tub.ods.rdf4led.distributed.rdf;

import dev.insight.rdf4led.common.util.ArrayUtil;
import dev.insight.rdf4led.rdf.dictionary.codec.RDFNodeType;
import dev.insight.rdf4led.rdf.dictionary.vocab.LangTag;
import dev.insight.rdf4led.rdf.dictionary.vocab.XSD;
import dev.insight.rdf4led.rdf.graph.Triple;
import dev.insight.rdf4led.rdf.parser.lang.LangEngine;
import dev.insight.rdf4led.rdf.parser.lang.Token;
import dev.insight.rdf4led.rdf.parser.lang.TokenType;
import dev.insight.rdf4led.rdf.parser.lang.Tokenizer;

import java.util.Iterator;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 25.04.19
 */
public class LangNtriples extends LangEngine implements Iterator<Triple<Long>> {

    private Dictionary dictionary;



    public LangNtriples(Tokenizer tokens, Dictionary dictionary) {
        super(tokens);
        this.dictionary = dictionary;
    }



    public Triple<Long> createTriple(Token sToken, Token pToken, Token oToken) {
        Long s = this.create(sToken);
        Long p = this.create(pToken);
        Long o = this.create(oToken);
        return new Triple<Long>(s, p, o);
    }



    public boolean hasNext() {
        return super.moreTokens();
    }



    public Triple next() {
        throw new UnsupportedOperationException();
    }



    public void remove() {
        throw new UnsupportedOperationException();
    }



    protected final void checkIRI(Token token) {
        if (token.getType() != TokenType.IRI) {
            throw new IllegalArgumentException("Predicate must be IRI");
        }
    }



    protected final void checkIRIOrBNode(Token token) {
        if (token.getType() != TokenType.IRI) {
            if (token.getType() != TokenType.BNODE) {
                throw new IllegalArgumentException("Predicate must be IRI + " + token.getImage());
            }
        }
    }



    protected final void checkRDFTerm(Token token) {
        switch (token.getType()) {
            case IRI:
            case BNODE:
                return;
            case STRING:
                return;
            case STRING2:
                return;
            case LITERAL_LANG:
            case LITERAL_DT:
                return;
            default:
                throw new IllegalArgumentException("Predicate must be IRI " + token.getImage());
        }
    }



    public Triple parseOne() {
        Token sToken = null;
        Token pToken = null;
        Token oToken = null;
        try {
            sToken = this.nextToken();
            pToken = this.nextToken();
            oToken = this.nextToken();
//            this.checkIRIOrBNode(sToken);
//            this.checkIRI(pToken);
//            this.checkRDFTerm(oToken);
        } catch (Exception e) {
            for (Token DOT = this.nextToken(); DOT.getType() != TokenType.DOT; DOT = this.nextToken()) {
            }
            return null;
        }

        for (Token DOT = this.nextToken(); DOT.getType() != TokenType.DOT; DOT = this.nextToken()) {
        }

        return this.createTriple(sToken, pToken, oToken);


    }



    private long create(Token token) {
        String str = token.getImage();

        switch (token.getType()) {
            case IRI: {
                return createIRI(str);
            }
            case BNODE: {
                //TODO blank node is not supported :(
                return createBNODE(str);
            }
            case STRING:
            case STRING2:
            case STRING1:
            case LONG_STRING1:
            case LONG_STRING2: {
                return creatPlainLiteral(str);
            }
            case LITERAL_LANG: {
                return createLangLiteral(token.getImage2(), str);
            }

            case LITERAL_DT: {
                Token tokenDT = token.getSubToken();
                String uriStr;
                switch (tokenDT.getType()) {
                    case IRI:
                        uriStr = tokenDT.getImage();
                        break;
                    case PREFIXED_NAME:
                        String prefix = tokenDT.getImage();
                        String suffix = tokenDT.getImage2();
                        throw new UnsupportedOperationException("N-turtles with prefix??? " + prefix + " : " + suffix);
                    default:
                        uriStr = "";
                }

                return createTypedLiteral(str, uriStr);
            }
            case PREFIXED_NAME:

            case DECIMAL: {
                return createDecimal(str);
            }

            case DOUBLE: {
                return createDouble(str);
            }

            case INTEGER: {
                return createInteger(str);
            }
            default:
                return 0;
        }
    }



    public long createInteger(String str) {
        int hash = this.dictionary.getHash(str, (byte) 0, (byte) 3);
        int node = this.dictionary.createTypedLiteral(str, (byte) 3);
        return ArrayUtil.int2long(hash, node);
    }



    public long createDouble(String str) {
        int hash = this.dictionary.getHash(str, (byte) 0, (byte) 2);
        int node = this.dictionary.createTypedLiteral(str, (byte) 2);
        return ArrayUtil.int2long(hash, node);
    }



    public long createDecimal(String str) {
        int hash = this.dictionary.getHash(str, (byte) 0, (byte) 11);
        int node = this.dictionary.createTypedLiteral(str, (byte) 11);
        return ArrayUtil.int2long(hash, node);
    }



    public long createTypedLiteral(String str, String uriStr) {
        if (XSD.xsd.getSuffix(uriStr) != 0) {
            int hash = this.dictionary.getHash(str, (byte) 0, XSD.xsd.getSuffix(uriStr));
            int node = this.dictionary.createTypedLiteral(str, XSD.xsd.getSuffix(uriStr));
            return ArrayUtil.int2long(hash, node);
        }

        int hash = this.dictionary.getHash(str, (byte) 0, RDFNodeType.LITERAL);
        int node = this.dictionary.createPlainLiteral(str + "^^" + uriStr);
        return ArrayUtil.int2long(hash, node);
    }



    public long createLangLiteral(String lang, String str) {
        byte langTag = LangTag.langtag.getTag(lang);

        int hash = this.dictionary.getHash(str, langTag, RDFNodeType.LITERALHASLANG);
        int node = this.dictionary.createLangLiteral(str, langTag);

        return ArrayUtil.int2long(hash, node);
    }



    public long creatPlainLiteral(String str) {
        int hash = this.dictionary.getHash(str, (byte) 0, RDFNodeType.LITERAL);
        int node = this.dictionary.createPlainLiteral(str);
        return ArrayUtil.int2long(hash, node);
    }



    public long createBNODE(String str) {
        int hash = this.dictionary.getHash(str, (byte) 0, RDFNodeType.BLANK);
        int node = this.dictionary.createBNode("");
        return ArrayUtil.int2long(hash, node);
    }



    public long createIRI(String str) {
        int hash = this.dictionary.getHash(str, (byte) 0, RDFNodeType.URI);
        int node = this.dictionary.createURI(str);
        return ArrayUtil.int2long(hash, node);
    }

}

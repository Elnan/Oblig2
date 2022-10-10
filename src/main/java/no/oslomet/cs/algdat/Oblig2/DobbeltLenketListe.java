package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import javax.swing.plaf.nimbus.NimbusStyle;
import java.util.*;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {

    }

    public DobbeltLenketListe(T[] a) {
        if (a == null) throw new NullPointerException("Tabellen a er null!");

        Node<T> forrigeNode = null;
        boolean foersteElement = true;
        endringer = 0;


        for (T verdi : a) {
            if (verdi == null) {
                continue;
            }
            Node<T> node = new Node<>(verdi);
            if (foersteElement) {
                hode = node;
                foersteElement = false;
            }
            if (forrigeNode != null) {
                node.forrige = forrigeNode;
                forrigeNode.neste = node;
            }
            forrigeNode = node;
        }

        int teller = 0;
        if (hode != null) {
            Node<T> node = hode;

            do {
                teller++;
                hale = node;
                node = node.neste;

            } while (node != null);

        }
        antall = teller;


    }


    public Liste<T> subliste(int fra, int til) {

        fratilKontroll(antall, fra, til);
        DobbeltLenketListe<T> nyListe = new DobbeltLenketListe<>();
        Node<T> node = finnNode(fra);

        int teller = 0;
        for (int i = fra; i < til; i++) {
            if (node != null) {
                nyListe.leggInn(node.verdi);
                node = node.neste;
                teller++;
            }
        }
        nyListe.antall = teller;
        nyListe.endringer = 0;
        return nyListe;
    }

    private void fratilKontroll(int antall, int fra, int til) {

        if (fra < 0 || fra > antall || til > antall || til < 0) {
            throw new IndexOutOfBoundsException("Intervallet du oppga er utenfor 0 -> " + antall + " eller at fra > til");
        }
        if (fra > til) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int antall() {

        Node<T> current = hode;
        int teller = 0;
        while (current != null) {
            teller++;
            current = current.neste;
        }
        return teller;
    }

    @Override
    public boolean tom() {
        return antall() == 0;
    }


    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi);
        Node<T> node = new Node<>(verdi);
        if (hale == null) {
            hode = hale = node;
        } else {
            hale.neste = node;
            node.forrige = hale;
            hale = node;
        }
        antall++;
        endringer++;

        return true;
    }

    private Node<T> finnNode(int index) {

        if (index < 0 || index > antall) {
            throw new IndexOutOfBoundsException("Ugyldig indeks oppgitt");
        }
        Node<T> node;

        if (index <= antall / 2) {
            node = hode;
            for (int i = 0; i < index; i++) {
                node = node.neste;
            }

        } else {
            if (index == antall) {
                return null;
            }
            node = hale;
            for (int i = antall - 1; i > index; i--) {
                node = node.forrige;
            }
        }
        return node;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi);
        Node<T> nyNode = new Node<>(verdi);

        if (indeks < 0 || indeks > antall) {
            throw new IndexOutOfBoundsException("Indeks er mindre enn null");
        } else if (hode == null) {
            hode = hale = nyNode; //Listen er tom
        } else {
            //Skal legges mellom to andre verdier
            Node<T> gammelNode = finnNode(indeks);
            if (hode.equals(gammelNode)) { //Verdien legges først
                hode = nyNode;
                nyNode.neste = gammelNode;
                gammelNode.forrige = hode;
            } else if (gammelNode == null) {
                hale.neste = nyNode;
                nyNode.forrige = hale;
                hale = nyNode;

            } else { //Verdien skal legges mellom
                nyNode.forrige = gammelNode.forrige;
                nyNode.neste = gammelNode;
                gammelNode.forrige.neste = nyNode;
                gammelNode.forrige = nyNode;
            }
        }
        antall++;
        endringer++;
    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        if (hode == null || verdi == null) {
            return -1;
        }
        Node<T> node = hode;
        int svar = 0;
        boolean funnet = false;
        do {
            if (verdi.equals(node.verdi)) {
                funnet = true;
                break;
            }
            node = node.neste;
            svar++;
        }
        while (node != null);

        return funnet ? svar : -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        indeksKontroll(indeks, false);
        Objects.requireNonNull(nyverdi);
        endringer++;
        Node<T> node = finnNode(indeks);
        T gammelverdi = node.verdi;
        node.verdi = nyverdi;

        return gammelverdi;
    }

    @Override
    public boolean fjern(T verdi) {
        Node<T> node = hode;
        boolean fjernet = false;

        if (verdi == null || node == null) {
            return false;
        }
        do {
            if (node.verdi.equals(verdi)) {
                if (hode.equals(node)) {
                    if (antall == 1) {
                        hode = hale = null;
                    } else {
                        hode.neste.forrige = null;
                        hode = hode.neste;
                    }

                } else if (hale.equals(node)) {
                    hale.forrige.neste = null;
                    hale = hale.forrige;
                } else {
                    node.forrige.neste = node.neste;
                    node.neste.forrige = node.forrige;
                }

                fjernet = true;
                antall--;
                endringer++;
                break;
            }
            node = node.neste;
        }
        while (node != null);


        return fjernet;

    }

    @Override
    public T fjern(int indeks) {
        if (indeks < 0 || indeks >= antall) {
            throw new IndexOutOfBoundsException("Indeksen finnes ikke");
        }
        Node<T> node = finnNode(indeks);
        if (node != null) {
            if (hode.equals(node)) {
                if (antall == 1) {
                    hode = hale = null;
                } else {
                    hode.neste.forrige = null;
                    hode = hode.neste;
                }

            } else if (hale.equals(node)) {
                hale.forrige.neste = null;
                hale = hale.forrige;
            } else {
                node.forrige.neste = node.neste;
                node.neste.forrige = node.forrige;
            }
            antall--;
            endringer++;
            return node.verdi;

        } else {
            throw new IndexOutOfBoundsException("Indeks finnes ikke");
        }
    }

    @Override
    public void nullstill() {
        endringer = 0;
        antall = 0;
        if (hode == null) {
            return;
        }
        Node<T> node = hode;
        Node<T> temp;
        do {
            temp = node.neste;
            node.forrige = null;
            node.neste = null;
            node.verdi = null;
            node = temp;
        } while (node != null);
        hode = hale = null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        if (hode != null) {
            Node<T> node = hode;

            do {
                builder.append(node.verdi);
                if (node.neste != null) {
                    builder.append(", ");
                }
                node = node.neste;
            } while (node != null);
        }
        builder.append("]");

        return builder.toString();
    }

    public String omvendtString() {
        StringBuilder builder = new StringBuilder("[");
        if (hale != null) {
            Node<T> node = hale;

            do {
                builder.append(node.verdi);
                if (node.forrige != null) {
                    builder.append(", ");
                }
                node = node.forrige;
            } while (node != null);

        }
        builder.append("]");

        return builder.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            denne = finnNode(indeks);
            if (denne == null) {
                throw new IndexOutOfBoundsException("Indeks er ugyldig");
            }
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            if (iteratorendringer != endringer) {
                throw new ConcurrentModificationException("Endringer og iteratorendringer stemmer ikke overens");
            }
            if (!hasNext()) {
                throw new NoSuchElementException("Listen har ikke flere elementer");
            }
            fjernOK = true;
            T verdi = denne.verdi;
            denne = denne.neste;
            return verdi;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe



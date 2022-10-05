package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;


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
        for (int i = fra; i<til; i++) {
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

    //Se nærmere på oppgaveteksten. Gir ikke mening
    private void fratilKontroll(int antall, int fra, int til) {

        if (fra < 0 || fra > antall || til > antall || til < 0) {
            throw new IndexOutOfBoundsException("Intervallet du oppga er utenfor 0 -> " + antall + " eller at fra > til");
        }
        if (fra > til) {
            throw new IllegalArgumentException();
        }

        //I oppgave 3b ber du om en IndexOutOfBoundsException istedenfor en
        // ArrayIndexOutOfBoundsException, men det eksisterer jo fra før? Skjønner ikke hva du vil.

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

        Node<T> node;

        if (index <= antall / 2) {
            node = hode;
            for (int i = 0; i < index; i++) {
                node = node.neste;
            }

        } else {
            node = hale;
            for (int i = antall; i > index+1; i--) {
                node = node.forrige;
            }
        }
            return node;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
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
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
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



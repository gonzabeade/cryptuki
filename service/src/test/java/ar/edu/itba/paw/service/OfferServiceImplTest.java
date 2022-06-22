package ar.edu.itba.paw.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

//Los únicos métodos interesantes para testear en esta clase consisten en la creación de filtros lo cuales
//son pasados al Dao como parámetros y la única forma que encontramos de testear esto es testeando el return value del Dao
//sin embargo este está siendo mockeado y no tiene sentido validar que ese valor sea correcto
//Además, las validaciones del estilo "una oferta solo puede ser modificada por el vendedor" se testean en CustomPreAuthorizerTest
//Por estos dos motivos esta clase no cuenta con tests
@RunWith(MockitoJUnitRunner.class)
public class OfferServiceImplTest {

    @Test
    public void emptyTest(){

    }

}

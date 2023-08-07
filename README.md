# Final project for Web Application Projects assignment

The project is fundamentally a P2P crypto marketplace, where cryptocurrencies are to be bought or sold anywhere in cash. 

The structure of the project is a multi-module Maven project that bundles a frontend (React-based SPA client) and backend (Spring-based API REST) into a single WAR ready to be deployed and run under the same context.

## Backend
### Structure

The overall project structure follows a model-view-controller (MVC) design pattern:

- `interfaces`: Declares the contracts for both the `/services` and `/persistence` implementations
- `models`: Declares the domain (ORM-based entities for JPA)
- `persistence`: Implements the contracts declared over `/interfaces`, it's the link between the database and the application
- `services`: Implements the contracts declared over `/interfaces`, it's the link between the controllers and the persistence layer
- `webapp`: Implements the API REST endpoints, uses the service layer.

### Dependencies
The backend runs on the following plugins/dependencies:
- Jersey
- Jax-RS
- JUnit4 + Mockito
- Spring 4 + Spring Security
- Spring HATEOAS + MapStruct
- Hibernate JPA + PSQL

### Functionality
The backend is an API REST that consists on three main filters that execute in order:
- Authorization filter (spring-security)
- Jersey filter (servlet-container)
- Default spring filter

Every authorized request over `/api/**` passes through the security filter which checks for either the `Basic` or `Bearer` authorization header. In the case the provided header is `Basic` and the credentials are correct, a JWT token is generated and appended in the response as `Authorization: Bearer <generated-token>`

After this, the Jersey filter takes over and tries to find the endpoint corresponding to the request, in case it's not found, the request is forwarded to the spring default filter which allows serving static content on which we serve the react `/index.html` as we can see over the [web.xml](https://github.com/gonzabeade/cryptuki/blob/frontend-views/webapp/src/main/webapp/WEB-INF/web.xml)


### Build

To build the project just run (you can opt to skip tests):
```
mvn clean install --DskipTests
```

## Frontend

The frontend uses the following libraries/modules/frameworks:
- `react-router`
- `i18next`
- `jest`

For standalone dev deployment you can navigate to the `/spa-client` folder and run:
```
npm start
```

To execute the tests run:
```
npm test
```

For a production build run:
```
npm run build
```

# Authors
- mdedeu@itba.edu.ar
- gbeade@itba.edu.ar
- shadad@itba.edu.ar
- scastagnino@itba.edu.ar


# License 
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

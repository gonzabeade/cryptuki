import React from 'react';

const Index = () => {
    return <div>
        <div className="flex flex-col">
            <div className="flex flex-col my-auto mx-7">
                <h1 className="font-sans font-polard text-xl font-semibold">
                    Reclamo
                    <b>
                        15
                    </b>
                </h1>
                <h3 className="text-gray-300">
                    Efectuado el
                    15/12/15
                </h3>
            </div>
            <div className="flex flex-col  my-auto mx-7">
                <h1 className="font-sans font-polard text-lg font-semibold">
                    Usuario
                    : <b>
                    Santiago
                </b>
                </h1>
                <h3 className="text-gray-600 w-60 overflow-y-hidden truncate">
                    Comentario
                    : El hombre no me deposito</h3>
            </div>
        </div>
        {/*<c:if test="${param.complainStatus != 'CLOSED'}">*/}
        {/*    <div className="flex flex-row my-3 mx-auto">*/}

        {/*        {(if true)? }*/}
        {/*        <if test="${param.complainStatus == 'PENDING'}">*/}
        {/*            <a href="<c:url value="/admin/complaint/${param.complainId}"/>"*/}
        {/*               className=" text-center pb-2 px-5 pt-2 rounded-lg bg-stormd max-h-14 text-polard my-auto">*/}
        {/*                <messages:message code="see"/>*/}
        {/*            </a>*/}
        {/*        </if>*/}
        {/*    </div>*/}
        {/*</c:if>*/}
    </div>;
};

export default Index;
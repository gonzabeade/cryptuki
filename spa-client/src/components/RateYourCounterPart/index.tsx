import React, {useState} from 'react';
import ConfirmationToggle from "../ConfirmationToggle";

type RateYourCounterPartProps = {
    usernameRater:string,
    usernameRated:string,
    tradeId:number
}

const RateYourCounterPart:React.FC<RateYourCounterPartProps>= ({usernameRated, usernameRater,  tradeId}) => {

    const [alreadyRated, setAlreadyReady] = useState<boolean>(false);
    const [rating, setRating] = useState<number>();
    function hoverOnRating(number:number) {
        let element;
        for(let i = 1; i <= number; i++){
            element = document.getElementById("star"+i);
            element?.classList.remove("fa-star-o");
            element?.classList.add("fa-star");
        }
    }
    function leaveHoverOnRating(number:number) {
        let element;
        for(let i = 1; i <= number; i++){
            element = document.getElementById("star"+i);
            element?.classList.remove("fa-star");
            element?.classList.add("fa-star-o");
        }

    }
    function setRatingAndSend(rating:number) {
        let button =  document.getElementById("sendRating");
        setRating(rating * 2);
        button?.click();
    }
    return (
        <>
            {alreadyRated &&
                <>
                    <h1 className="text-polard font-roboto  font-bold text-center text-3xl mx-auto">
                        Rate {usernameRated}
                    </h1>
                    <form method="post">

                        {/*<input type="hidden"/> tradeId*/}
                        <div className="flex flex-col">
                            <div className="flex flex-row mx-auto mt-3">
                                        <span className=" cursor-pointer fa fa-star-o text-orange-400 text-3xl" id="star1"
                                              onClick={()=>setRatingAndSend(1)} onMouseLeave={()=>leaveHoverOnRating(1)}
                                              onMouseOver={()=>hoverOnRating(1)}></span>
                                <span className=" cursor-pointer fa fa-star-o text-orange-400 text-3xl" id="star2"
                                      onClick={()=>setRatingAndSend(2)} onMouseLeave={()=>leaveHoverOnRating(2)}
                                      onMouseOver={()=>hoverOnRating(2)}></span>
                                <span className=" cursor-pointer fa fa-star-o text-orange-400 text-3xl" id="star3"
                                      onClick={()=>setRatingAndSend(3)} onMouseLeave={()=>leaveHoverOnRating(3)}
                                      onMouseOver={()=>hoverOnRating(3)}></span>
                                <span className=" cursor-pointer fa fa-star-o text-orange-400 text-3xl" id="star4"
                                      onClick={()=>setRatingAndSend(4)} onMouseLeave={()=>leaveHoverOnRating(4)}
                                      onMouseOver={()=>hoverOnRating(4)}></span>
                                <span className=" cursor-pointer fa fa-star-o text-orange-400 text-3xl" id="star5"
                                      onClick={()=>setRatingAndSend(5)} onMouseLeave={()=>leaveHoverOnRating(5)}
                                      onMouseOver={()=>hoverOnRating(5)}></span>

                            </div>
                            <div className="flex flex-row">
                                <input type="hidden" value="0" /> {/*rating*/}
                                <button type="submit" id="sendRating"
                                        className="bg-frostdr text-white  mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto active:cursor-progress hidden">
                                   Send
                                </button>
                            </div>
                        </div>

                    </form>
                </>}
            {!alreadyRated &&
                <div className="mb-5 mt-5">
                    <ConfirmationToggle title={"Rating sent"}/>
                </div>
            }
        </>
    );
};

export default RateYourCounterPart;
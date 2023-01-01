export default class Result<T> {
    private readonly data: T;
    private readonly _statusCode: number;

    private constructor(data: T, statusCode:number) {
        this.data = data;
        this._statusCode = statusCode;
    }

    public getData(): T {
        return this.data;
    }

    public static ok<T>(data: T) {
        return new Result<T>(data, 200);
    }

    get statusCode(): number {
        return this._statusCode;
    }

    public static fail<T>(data:T, statusCode:number){
        return new Result<T>(data, statusCode);
    }
}
export default function Loader() {
    return (
        <div className="animate-pulse space-y-3">
            <div className="grid lg:grid-cols-4 md:grid-cols-2 sm:grid-cols-1 gap-3 pb-3">
                <div className="h-80 w-48 bg-slate-300 rounded-lg"> </div>
                <div className="h-80 w-48 bg-slate-300 rounded-lg"> </div>
                <div className="h-80 w-48 bg-slate-300 rounded-lg"> </div>
                <div className="h-80 w-48 bg-slate-300 rounded-lg"> </div>
            </div>
        </div>
    );
}
import Image from 'next/image'


interface Props {
    imgSrc: string,
    videoName: string
}


export default function RecommendedList({imgSrc, videoName} : Props) {
  return (
    <div className="flex items-center mb-2 cursor-pointer px-3 py-2 hover:bg-gray-900 duration-200 ease-in-out overflow-hidden">
        <div className='sm:w-[60px] sm:h-[60px] lg:w-[140px] lg:h-[80px] relative'>
            <Image
                src={imgSrc}
                fill
                alt=""
                className='rounded-lg object-cover'
            />
        </div>

        <div className='ml-2 flex-1'>
            <h3 className='lg:text-[16px] sm:text-[12px]'>
                {videoName} 
                <span className='block text-[12px] text-gray-400'>Ninja</span>
            </h3>
            <div className='flex items-center mt-2'>
                <p className='text-[14px] font-bold text-gray-500'>40:30</p>
                <p className='text-[14px] font-bold text-gray-500 ml-6'>36,000</p>
            </div>
        </div>
    </div>
  )
}

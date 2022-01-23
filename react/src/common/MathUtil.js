export const primeFactorization = (number) => {
  const primeNumbers = [];

  let divisor, squareRoot, mok, nmg;

  do {
    divisor = 2;
    squareRoot = parseInt(Math.sqrt(number));
    
    do {
      if(divisor > squareRoot) {
        divisor = number;
        break;
      }
      mok = parseInt(number / divisor);
      nmg = number - (mok * divisor);
    } while (nmg != 0 && divisor++)

    primeNumbers.push(divisor);
  } while(number != divisor && (number = mok))

  return primeNumbers;
}

export const getPrimes = (number) => {
  const primeNumbers = [];

  let divisor, squareRoot, mok, nmg;

  do {
    divisor = 2;
    squareRoot = parseInt(Math.sqrt(number));
    
    do {
      if(divisor > squareRoot) {
        divisor = number;
        break;
      }
      mok = parseInt(number / divisor);
      nmg = number - (mok * divisor);
    } while (nmg != 0 && divisor++)

    if(!primeNumbers.includes(divisor))
      primeNumbers.push(divisor);
  } while(number != divisor && (number = mok))

  return primeNumbers;
}